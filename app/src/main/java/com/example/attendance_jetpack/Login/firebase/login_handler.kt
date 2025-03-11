package com.example.attendance_jetpack.Login.firebase

import android.content.Context
import android.widget.Toast

object login_handler {
    fun handleLogin(context: Context, userType: String, studentId: String, teacherId: String, password: String) {
        if (userType.isNotEmpty() &&
            ((userType == "Student" && studentId.isNotEmpty()) || (userType == "Teacher" && teacherId.isNotEmpty())) &&
            password.isNotEmpty()
        ) {
            val userId = if (userType == "Student") studentId else teacherId
            Login_Auth.loginUser(
                context = context,
                userId = userId,
                password = password,
                role = userType,
                onLoginSuccess = {
                    Toast.makeText(context, "Login Successful!", Toast.LENGTH_SHORT).show()
                },
                onLoginFailure = { errorMessage ->
                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                }
            )
        } else {
            Toast.makeText(context, "Please fill in all fields!", Toast.LENGTH_SHORT).show()
        }
    }
}