package com.example.attendance_jetpack.Signup.firebase

import android.content.Context
import android.widget.Toast

object SignupHandler{


    fun handleSignup(context: Context, username:String, email:String, userType:String, studentId:String, teacherId:String, password:String){
        if (userType=="Student"&& username.isNotEmpty()&& email.isNotEmpty() && studentId.isNotEmpty()&& password.isNotEmpty() ){
            val generatedEmail ="${studentId.trim()}@bvec.com"
            if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
            {
                Toast.makeText(context,"Invalid Email address",Toast.LENGTH_SHORT).show()
                return
            }
            AuthManager.registerUser(context,username,email,generatedEmail,password,studentId,"Student")
        }
        else if (userType=="Teacher"&& username.isNotEmpty()&& email.isNotEmpty() && teacherId.isNotEmpty()&& password.isNotEmpty()){
            val generatedEmail ="${teacherId.trim()}@bvec.com"
            AuthManager.registerUser(context,username,email,generatedEmail,password,teacherId,"Teacher")
        }
        else{
            Toast.makeText(context,"Please fill in all fields!!",Toast.LENGTH_SHORT).show()
        }
    }
}