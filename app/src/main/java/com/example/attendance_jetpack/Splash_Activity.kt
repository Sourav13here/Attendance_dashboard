package com.example.attendance_jetpack

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.window.SplashScreen
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.attendance_jetpack.ui.theme.Attendance_JetpackTheme

class Splash_Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Attendance_JetpackTheme {
                    SplashScreen()
            }

            Handler().postDelayed(
                {
                    startActivity(Intent(this,MainActivity::class.java))
                    finish()
                },2000

            )
        }
    }
}

@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center


    ) {
       Image(painter = painterResource(R.drawable.splash), contentDescription = "",
           modifier = Modifier.size(300.dp))
    }
}




@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Attendance_JetpackTheme {
        SplashScreen()

    }
}