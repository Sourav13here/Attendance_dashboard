@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.attendance_jetpack.Login

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.attendance_jetpack.Login.firebase.login_handler
import com.example.attendance_jetpack.MainActivity
import com.example.attendance_jetpack.R
import com.example.attendance_jetpack.Signup.Signup

@ExperimentalMaterial3Api
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginPage() {
    var password by remember { mutableStateOf("") }
    var userType by remember { mutableStateOf("Student") }
    var userId by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "LOGIN",
                        style = TextStyle(fontSize = 24.sp, fontStyle = FontStyle.Italic),
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    // Top Dark Section
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.4f) // 40% height
                            .background(Color(0xFF6D6464))
                            .padding(start=150.dp, top = 50.dp), // Dark gray

                    ) {
                        Image(
                            painter = painterResource(R.drawable.logonew),
                            contentDescription = "Logo",
                            modifier = Modifier.size(100.dp)
                        )
                    }

                    // Bottom White Section
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.6f) // 60% height
                            .background(Color.White),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        Spacer(modifier = Modifier.height(100.dp)) // Space for the overlapping card
                    }
                }

                // Login Card (Overlapping Two Backgrounds)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = 200.dp), // Moves it up to overlap both sections
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.LightGray),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                        modifier = Modifier.padding(horizontal = 16.dp)
                            .height(300.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            OutlinedTextField(
                                value = userId,
                                onValueChange = { userId = it },
                                label = { Text(if (userType == "Student") "ASTU Roll no" else "Teacher ID") },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            OutlinedTextField(
                                value = password,
                                onValueChange = { password = it },
                                label = { Text("Password") },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                                trailingIcon = {
                                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                        Icon(
                                            painter = painterResource(
                                                if (passwordVisible) R.drawable.baseline_visibility_24
                                                else R.drawable.baseline_visibility_off_24
                                            ),
                                            contentDescription = "Toggle Password Visibility"
                                        )
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "Forgot Password?",
                                modifier = Modifier.clickable { /* Handle forgot password */ }
                                    .padding(start=120.dp),
                                style = TextStyle(fontSize = 14.sp, color = Color.Blue)
                            )
                        }
                    }
                }

                // Floating Login Button (Slightly Overlapping Card)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = 470.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = {
                            login_handler.handleLogin(
                                context = context,
                                userType = userType,
                                studentId = if (userType == "Student") userId else "",
                                teacherId = if (userType == "Teacher") userId else "",
                                password = password
                            )
                        },
                        modifier = Modifier
                            .width(150.dp)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD7A8A8)),
                        shape = RoundedCornerShape(24.dp),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Login", fontSize = 16.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                painter = painterResource(R.drawable.baseline_login_24),
                                contentDescription = "Login Icon"
                            )
                        }
                    }
                }

                // Sign-up Row at Bottom
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .background(Color(0xFFD7A8A8)),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text("Don't have an account?", fontSize = 14.sp)
                        Spacer(modifier = Modifier.width(4.dp))
                        Button(
                            onClick = {
                                val intent = Intent(context, Signup::class.java)
                                context.startActivity(intent)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA07A7A))
                        ) {
                            Text("Sign up")
                        }
                    }
                }
            }
        }
    )


}

@Preview(showSystemUi = true)
@Composable
fun PreviewLoginPage() {
    LoginPage()
}
