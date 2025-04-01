package com.example.attendance_jetpack


import android.content.Intent
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.attendance_jetpack.ui.theme.CoralRed
import com.example.attendance_jetpack.ui.theme.CrimsonRed
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentDashboard( isCR: Boolean, attendanceData: List<AttendanceItem>) {

    val context= LocalContext.current
    val isPreview = LocalInspectionMode.current
    val studentID = if(!isPreview)FirebaseAuth.getInstance().currentUser?.uid else null
    var studentName by remember { mutableStateOf((if(isPreview)"Preview Student" else "")) }

    var isCr by remember { mutableStateOf(isCR) }



    var pendingVerifications by remember { mutableStateOf(0) }


    if(!isPreview) {
        LaunchedEffect(studentID) {
            if (studentID != null) {
                FirebaseFirestore.getInstance().collection("students").document(studentID)
                    .get()
                    .addOnSuccessListener { document ->
                        if (document != null && document.exists()) {
                            studentName = document.getString("username") ?: "Student"
                        }
                    }
                    .addOnFailureListener { e ->
                        Log.e("Firestore", "Error fetching student data: ${e.message}")
                    }

                FirebaseFirestore.getInstance().collection("Student").document(studentID)
                    .get()
                    .addOnSuccessListener { document ->
                        if (document != null && document.exists()) {
                            isCr = document.getBoolean("Cr") ?: false
                            Log.d("Firestore", "Updated CR status: $isCR")
                        }
                    }
                    .addOnFailureListener { e ->
                        Log.e("Firestore", "Error fetching CR status: ${e.message}")
                    }

            }
        }

        LaunchedEffect(isCr) {
            if (isCr) {
                val db = FirebaseFirestore.getInstance().collection("Student")
                    .whereEqualTo("verified", false)

                db.addSnapshotListener { querySnapshot, error ->
                    if (error != null) {
                        Log.e("Firestore", "Error fetching pending verifications: ${error.message}")
                        return@addSnapshotListener
                    }
                    pendingVerifications = querySnapshot?.size() ?: 0
                    Log.d("Firestore", "Pending verifications updated: $pendingVerifications")
                }
            }
        }
    }




    Scaffold(


        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Student Dashboard", fontSize = 23.sp)
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(painter = painterResource(R.drawable.profile), contentDescription = "")
                    }
                }
            )
        },
        floatingActionButton = {
            if (isCr) {
                Box(modifier = Modifier.padding(16.dp)
                ) {
                    FloatingActionButton(

                        onClick = {
                            try {
                                val intent = Intent(context, Cr_verification::class.java)
                                context.startActivity(intent)
                            } catch (e: Exception) {
                                Log.e("Intent Error", "Error opening CR Verification Activity", e)
                            }
                        },

                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text("Verify")
                    }
                    if (pendingVerifications>0){
                        BadgeBox(count=pendingVerifications)
                    }
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 7.dp)
            ) {
                Text(
                    text = " $studentName ",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(10.dp)) // ✅ Ensure spacing

                if (attendanceData.isEmpty()) {
                    Text(text = "No attendance records found", modifier = Modifier.padding(16.dp))
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f) // ✅ Fix for visibility
                            .padding(top = 10.dp)
                    ) {
                        items(attendanceData) { attendanceItem ->
                            AttendanceCard(attendanceItem)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AttendanceCard(attendanceItem: AttendanceItem) {
    val backgroundColor = when {
        attendanceItem.attendancePercentage < 65 -> CrimsonRed.copy(alpha = 1f) // Deep Red
        attendanceItem.attendancePercentage < 75 -> CoralRed.copy(alpha = 1f) // Light Red
        else -> Color.Green.copy(alpha = 1f) // Default Green Shade
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .padding(start = 10.dp, end = 10.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularProgressIndicator(
                progress = { attendanceItem.attendancePercentage / 100f },
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color.Green.copy(alpha = 0.4f)),
                color = backgroundColor,
                strokeWidth = 5.dp,
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = "${attendanceItem.className}: ${attendanceItem.attendancePercentage}%", fontSize = 16.sp)
        }
    }

}

// Sample data model
data class AttendanceItem(
    val className: String,
    val semester: String,
    val teacherName: String,
    val attendancePercentage: Int
)


@Composable
fun BadgeBox(count: Int) {
    Box(
        modifier = Modifier
            .size(24.dp)
            .background(Color.Red, shape = CircleShape)
            .border(2.dp, Color.White, CircleShape)

            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = count.toString(), color = Color.White, fontSize = 12.sp)
    }
}



// Sample Preview
@Preview(showBackground = true)
@Composable
fun PreviewStudentDashboard() {
    val sampleData = listOf(
        AttendanceItem("Math", "1st", "Mr. Sharma", 85),
        AttendanceItem("Physics", "2nd", "Dr. Patel", 72),
        AttendanceItem("Computer Science", "3rd", "Prof. Roy", 48)
    )
    StudentDashboard( isCR = true, attendanceData = sampleData)
}



