package com.example.attendance_jetpack.Signup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.attendance_jetpack.ui.theme.Attendance_JetpackTheme

class Signup : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Attendance_JetpackTheme {
                Signup_page()
                }
            }
        }
    }


