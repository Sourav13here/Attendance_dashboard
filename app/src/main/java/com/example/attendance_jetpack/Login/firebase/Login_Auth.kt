package com.example.attendance_jetpack.Login.firebase

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.collection.emptyLongSet
import com.example.attendance_jetpack.Student_dashboard
import com.example.attendance_jetpack.Student_verification
import com.example.attendance_jetpack.Teacher_dashboard
import com.example.attendance_jetpack.Teacher_verification
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.firestore.FirebaseFirestore

object Login_Auth {
    private val auth = FirebaseAuth.getInstance()
    private val db=FirebaseFirestore.getInstance()

    fun loginUser( context: Context, userId: String,
                   password: String,
                   role: String,
                   onLoginSuccess: () -> Unit,
                   onLoginFailure: (String) -> Unit)
    {
        val generatedEmail ="$userId@bvec.com"
        Log.d("AuthLoginManager", "Attempting login for: $generatedEmail")

        auth.signInWithEmailAndPassword(generatedEmail,password)
            .addOnCompleteListener { task->
                if(task.isSuccessful) {
                    checkUserVerification(context, userId, role)
                    onLoginSuccess()
                }


                else {
                    val exception = task.exception
                    val errorMessage = when  {
                        exception is FirebaseAuthInvalidUserException-> "No account found with this ID."
                        exception is FirebaseAuthInvalidCredentialsException -> "Incorrect password or invalid account"
                        exception?.message?.contains("no user record") == true ->
                            "No account found with this ID."
                        else -> exception?.localizedMessage ?: "Login Failed"
                    }
                    Log.e("AuthLoginManager", "Login Failed: $errorMessage")

                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                    onLoginFailure(errorMessage)
                }
            }

    }




    private fun checkUserVerification(context: Context,userId: String,role: String){
       db.collection(role).get().addOnSuccessListener { documents->
           val userRef = db.collection(role).document(userId)

           if (role=="Teacher"){
               if(documents.isEmpty){
                   userRef.set(mapOf("verified" to true))
                   navigateToDashboard(context,role,true)
               }
               else{
                   userRef.get().addOnSuccessListener { document ->
                       if(document.exists() && document.getBoolean("verified")==true){
                           navigateToDashboard(context,role,true)
                       }
                       else{
                           navigateToDashboard(context,role,false)
                       }
                   }
               }
           }
           else if (role=="Student"){
               if(documents.isEmpty){
                   userRef.set(mapOf("verified" to true,"Cr" to true))
                   navigateToDashboard(context,role,true)
               }
               else{
                   userRef.get().addOnSuccessListener { document->
                       if(document.exists() && document.getBoolean("verified")==true){
                           navigateToDashboard(context,role,true)
                       }
                       else{
                           navigateToDashboard(context,role,false)
                       }
                   }
               }
           }
       }

    }






    private fun navigateToDashboard(context: Context, role: String,isVerified:Boolean) {
        val intent = when{
            role=="Student" && isVerified-> Intent(context,Student_dashboard::class.java)
            role=="Student" -> Intent(context,Student_verification::class.java)
            role=="Teacher" && isVerified-> Intent(context,Teacher_dashboard::class.java)
            else->Intent(context,Teacher_verification::class.java)

        }
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
        Log.d("AuthLoginManager", "Navigated to $role dashboard")
    }




}