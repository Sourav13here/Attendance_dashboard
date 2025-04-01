package com.example.attendance_jetpack.Signup.firebase

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.example.attendance_jetpack.Login.Login
import com.example.attendance_jetpack.Login.LoginPage
import com.example.attendance_jetpack.Student_dashboard
import com.example.attendance_jetpack.Teacher_dashboard

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.firestore.FirebaseFirestore

object AuthManager {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    fun registerUser(
        context: Context,
        username: String,
        email: String,
        generatedEmail: String,
        password: String,
        userId: String,
        role: String,
        department:String="",
        semester:String=""
    ) {
        Log.d("AuthManager", "Attempting to register user: $username with email: $generatedEmail")

        auth.createUserWithEmailAndPassword(generatedEmail, password)
            .addOnCompleteListener { task ->


                if (task.isSuccessful) {
                    Log.d("AuthManager", "Firebase Auth Signup Successful")

                    val user = auth.currentUser
                    if (user != null) {
                        val userData = hashMapOf(
                            "username" to username,
                            "email" to email,
                            "userId" to userId,
                            "role" to role
                        )

//                        user.sendEmailVerification()
//                            .addOnSuccessListener {
//                                Toast.makeText(context,"Verification email sent",Toast.LENGTH_SHORT).show()
//                            }
//                            .addOnFailureListener { e->
//                                Log.e("AuthManager", "Failed to send verification email: ${e.message}")
//                            }


                        if(role=="Student"){
                            userData["department"]=department
                            userData["semester"]=semester
                        }



                        val collectionPath = if (role == "Student") "students" else "teachers"
                        db.collection(collectionPath).document(user.uid)
                            .set(userData)
                            .addOnSuccessListener {
                                Log.d("AuthManager", "User data stored successfully in Firestore")


                                    Toast.makeText(context, "Signup Successful!", Toast.LENGTH_SHORT).show()

                                val intent=Intent(context, Login::class.java)
                                context.startActivity(intent)

                            }
                            .addOnFailureListener { e ->
                                Log.e("AuthManager", "Failed to store user data: ${e.message}")

                                    Toast.makeText(context, "Failed to store data in Firestore", Toast.LENGTH_SHORT).show()

                            }
                    } else {
                        Log.e("AuthManager", "Firebase User is NULL after signup")

                            Toast.makeText(context, "Signup Failed (User is null)", Toast.LENGTH_SHORT).show()

                    }
                } else {
                    Log.e("AuthManager", "Signup Failed: ${task.exception?.message}")


                        if (task.exception is FirebaseAuthUserCollisionException) {
                            Toast.makeText(context, "Already registered, please log in!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(
                                context, task.exception?.localizedMessage ?: "Signup Failed", Toast.LENGTH_SHORT
                            ).show()
                        }

                }
            }
    }

//    private fun navigateToDashboard(context: Context, role: String) {
//        Log.d("AuthManager", "Navigating to Dashboard for $role")
//
//        val intent = if (role == "Student") {
//            Intent(context, Student_dashboard::class.java)
//        } else {
//            Intent(context, Teacher_dashboard::class.java)
//        }
//
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        context.startActivity(intent)
//        Log.d("AuthManager", "Intent started for $role")
//    }
}
