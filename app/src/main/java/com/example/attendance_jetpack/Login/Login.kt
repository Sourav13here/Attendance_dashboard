@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.attendance_jetpack.Login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.attendance_jetpack.ui.theme.Attendance_JetpackTheme


class Login : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent{
            Attendance_JetpackTheme {

                Login_page()

            }

        }
    }
}