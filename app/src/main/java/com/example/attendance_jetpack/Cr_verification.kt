package com.example.attendance_jetpack

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.attendance_jetpack.ui.theme.Attendance_JetpackTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Cr_verification : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Attendance_JetpackTheme {
                CrVerificationScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrVerificationScreen() {
    var unverifiedStudents by remember { mutableStateOf<List<StudentData>>(emptyList()) }
    val db = FirebaseFirestore.getInstance()
    val context = LocalContext.current
    val userid = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    // Fetch unverified students from Firestore
    LaunchedEffect(Unit) {
        db.collection("students").get()
            .addOnSuccessListener { result ->
                val students = mutableListOf<StudentData>()
                for (doc in result.documents) {
                    val student = doc.toObject(StudentData::class.java)?.copy(id = doc.id)
                    if (student != null) {
                        db.collection("Student").document(student.userId).get()
                            .addOnSuccessListener { studentDoc ->
                                val isVerified = studentDoc.getBoolean("verified") ?: false
                                if (!isVerified) {
                                    students.add(student)
                                    unverifiedStudents = students
                                }
                            }
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.e("CR Verification", "Error fetching students: ${exception.message}")
            }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Verify Students") },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                if (unverifiedStudents.isEmpty()) {
                    Text("No students to verify.", fontSize = 18.sp)
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(unverifiedStudents) { student ->
                            StudentVerificationCard(student) { action ->
                                updateStudentVerificationStatus(
                                    db, student.userId, action == "verify", context
                                ) {
                                    unverifiedStudents = unverifiedStudents.filterNot { it.userId == student.userId }
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

// **Student Card UI**
@Composable
fun StudentVerificationCard(student: StudentData, onAction: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = student.username, fontSize = 18.sp, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Roll No: ${student.userId}", fontSize = 14.sp)
            Text(text = "Email: ${student.email}", fontSize = 14.sp)
            Text(text = "Department: ${student.department}", fontSize = 14.sp)
            Text(text = "Semester: ${student.semester}", fontSize = 14.sp)

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = { onAction("reject") }) {
                    Text("Reject", color = MaterialTheme.colorScheme.error)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { onAction("verify") }) {
                    Text("Verify")
                }
            }
        }
    }
}

// **Single function to handle verification & rejection**
fun updateStudentVerificationStatus(
    db: FirebaseFirestore,
    userId: String,
    isVerified: Boolean,
    context: android.content.Context,
    onStudentUpdated: () -> Unit
) {
    db.collection("Student").document(userId)
        .update("verified", isVerified)
        .addOnSuccessListener {
            Toast.makeText(context, if (isVerified) "Student Verified" else "Student Rejected", Toast.LENGTH_SHORT).show()
            onStudentUpdated()
        }
        .addOnFailureListener { e ->
            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
}

// **Student Data Model**
data class StudentData(
    val id: String = "",
    val userId: String = "",
    val Uid : String ="",
    val username: String = "",
    val email: String = "",
    val department: String = "",
    val semester: String = "",
    val role: String = "Student"
)

@Preview(showBackground = true)
@Composable
fun PreviewVerification() {
    Attendance_JetpackTheme {
        CrVerificationScreen()
    }
}
