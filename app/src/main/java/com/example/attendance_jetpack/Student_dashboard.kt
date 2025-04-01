package com.example.attendance_jetpack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.attendance_jetpack.ui.theme.Attendance_JetpackTheme

class Student_dashboard : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Attendance_JetpackTheme {
                val sampleData = listOf(
                    AttendanceItem("Math", "1st", "Mr. Sharma", 85),
                    AttendanceItem("Physics", "2nd", "Dr. Patel", 72),
                    AttendanceItem("Computer Science", "3rd", "Prof. Roy", 48)
                )
                StudentDashboard(isCR = true,
                    attendanceData = sampleData)

                }
            }

}

}