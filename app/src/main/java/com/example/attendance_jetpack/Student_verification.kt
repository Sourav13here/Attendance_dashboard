package com.example.attendance_jetpack

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.attendance_jetpack.ui.theme.Attendance_JetpackTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Student_verification : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Attendance_JetpackTheme {
                Student_Verification()

                }
            }
        }
    }


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Student_Verification() {
    val context = LocalContext.current
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
    val db = FirebaseFirestore.getInstance()

    var statusText by remember { mutableStateOf("You are not verified yet. Please wait...") }
    var isLoading by remember { mutableStateOf(false) }

    fun checkVerificationStatus() {
        isLoading = true
        val userRef = db.collection("Student").document(userId)

        userRef.get().addOnSuccessListener { document ->
            isLoading = false
            if (document.exists() && document.getBoolean("verified") == true) {
                Toast.makeText(context, "You are now verified! Redirecting...", Toast.LENGTH_SHORT).show()
                navigateToStudentDashboard(context)
            } else {
                statusText = "Still not verified. Please wait..."
                Toast.makeText(context, "Still not verified. Try again later.", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener { e ->
            isLoading = false
            Log.e("StudentVerification", "Error checking verification: ${e.localizedMessage}")
            Toast.makeText(context, "Error checking verification. Try again later.", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Student Verification") }) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = statusText, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { checkVerificationStatus() },
                enabled = !isLoading
            ) {
                Text(text = if (isLoading) "Checking..." else "Refresh")
            }
        }
    }
}

private fun navigateToStudentDashboard(context: Context) {
    val intent = Intent(context, Student_dashboard::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    context.startActivity(intent)
    Log.d("StudentVerification", "Navigated to Student Dashboard")
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    Student_Verification()

}