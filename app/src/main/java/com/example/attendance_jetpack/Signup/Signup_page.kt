@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.attendance_jetpack.Signup

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.draw.scale
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
import com.example.attendance_jetpack.Login.Login
import com.example.attendance_jetpack.MainActivity
import com.example.attendance_jetpack.R
import com.example.attendance_jetpack.Signup.firebase.SignupHandler

@Composable
fun Signup_page() {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var userType by remember { mutableStateOf("Student") }
    var studentId by remember { mutableStateOf("") }
    var teacherId by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordvisible by remember { mutableStateOf(false) }
    var confirmpassword by remember { mutableStateOf("") }
    var confirmpasswordvisible by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var department by remember { mutableStateOf("") }
    var semester by remember { mutableStateOf("") }
    val context = LocalContext.current

    val departmentOptions = listOf("CSE", "ETE", "CE", "ME")
    val semesterOptions = listOf("1st", "2nd", "3rd", "4th", "5th", "6th", "7th", "8th")

    var expandedDepartment by remember { mutableStateOf(false) }
    var expandedSemester by remember { mutableStateOf(false) }


    Scaffold(topBar = {
        TopAppBar(title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "SIGN UP",
                    modifier = Modifier.wrapContentSize(),
                    style = TextStyle(fontSize = 24.sp, fontStyle = FontStyle.Italic)

                )
            }
        }, actions = {
            IconButton(onClick = {
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
            }) {
                Icon(
                    painter = painterResource(R.drawable.baseline_home_24),
                    contentDescription = "",
                    modifier = Modifier.fillMaxSize()
                )
            }
        })
    }, content = { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(start = 20.dp, end = 20.dp)
                .background(Color.White)
        ) {

            Column(
                Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth(),

                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(R.drawable.logonew),
                    contentDescription = "",
                    Modifier.size(50.dp)
                )
                Spacer(modifier = Modifier.size(5.dp))


                OutlinedTextField(value = username,
                    onValueChange = { username = it },
                    label = { Text("Username") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()

                )

                Spacer(modifier = Modifier.size(10.dp))

                OutlinedTextField(value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.size(6.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,

                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "User:", fontSize = 19.sp)

                    Spacer(modifier = Modifier.size(10.dp))

                    Row(verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { userType = " " }) {
                        RadioButton(selected = userType == "Student",
                            onClick = { userType = "Student" })
                        Text("Student")
                    }

                    Row(verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { userType = " " }) {
                        RadioButton(selected = userType == "Teacher",
                            onClick = { userType = "Teacher" })
                        Text("Teacher")

                    }
                    Spacer(Modifier.size(7.dp))


                }
                if (userType == "Student") {
                    OutlinedTextField(
                        value = studentId,
                        onValueChange = { studentId = it },

                        label = { Text("ASTU Roll no") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier= Modifier.size(10.dp))
                    Box(modifier = Modifier.fillMaxWidth().clickable { expandedSemester=true }) {
                        OutlinedTextField(
                            value = department,
                            onValueChange = { },
                            readOnly = true,
                            label = { Text("Department") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth().clickable { expandedSemester=true },
                            trailingIcon = {
                                IconButton(onClick = {expandedDepartment=true}) {
                                    Icon(painter = painterResource(id=R.drawable.baseline_arrow_drop_down_24),
                                        contentDescription = "Dropdown")
                                }
                            }
                        )
                        DropdownMenu(
                            expanded = expandedDepartment,
                            onDismissRequest = { expandedDepartment = false }

                        ) {
                            departmentOptions.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option) },
                                    onClick = {
                                        department = option
                                        expandedDepartment = false
                                    },
                                    modifier = Modifier.fillMaxWidth().clickable { expandedSemester=true }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.size(10.dp))

                    Box(modifier = Modifier.fillMaxWidth().clickable { expandedSemester=true }) {
                        OutlinedTextField(
                            value = semester,
                            onValueChange = { },
                            readOnly = true,
                            label = { Text("Semester") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            trailingIcon = {
                                IconButton(onClick = {expandedSemester=true}) {
                                    Icon(painter = painterResource(id=R.drawable.baseline_arrow_drop_down_24),
                                        contentDescription = "Dropdown")
                                }
                            }
                        )
                        DropdownMenu(
                            expanded = expandedSemester,
                            onDismissRequest = { expandedSemester = false }
                        ) {
                            semesterOptions.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option) },
                                    onClick = {
                                        semester = option
                                        expandedSemester = false
                                    }
                                )
                            }
                        }
                    }
                } else {
                    OutlinedTextField(value = teacherId,
                        onValueChange = { teacherId = it },
                        label = { Text("Teacher's ID") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                }

                Spacer(Modifier.size(10.dp))

                OutlinedTextField(value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = if (passwordvisible) {
                        VisualTransformation.None
                    } else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { passwordvisible = !passwordvisible }) {
                            Icon(
                                painter = painterResource(
                                    if (passwordvisible) {
                                        R.drawable.baseline_visibility_24
                                    } else (R.drawable.baseline_visibility_off_24)
                                ), contentDescription = ""
                            )
                        }
                    }

                )
                Spacer(Modifier.size(10.dp))

                OutlinedTextField(value = confirmpassword,
                    onValueChange = {
                        confirmpassword = it
                        passwordError = confirmpassword.isNotEmpty() && password != confirmpassword
                    },
                    label = { Text("Confirm Password") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = if (confirmpasswordvisible) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = {
                            confirmpasswordvisible = !confirmpasswordvisible
                        }) {
                            Icon(
                                painter = painterResource(if (confirmpasswordvisible) R.drawable.baseline_visibility_24 else R.drawable.baseline_visibility_off_24),
                                contentDescription = ""
                            )
                        }
                    })
                if (passwordError) {
                    Text(
                        text = "Passwords do not match",
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall

                    )
                }

                Spacer(modifier = Modifier.size(10.dp))

                Button(onClick = {SignupHandler.handleSignup(
                    context = context,
                    username= username,
                    email = email,
                    userType=userType,
                    studentId = studentId,
                    teacherId=teacherId,
                    password=password,
                    department=department,
                    semester=semester
                )


                }) {
                    Text("SIGNUP")
                }

                Spacer(modifier = Modifier.size(15.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "Already have an account?",
                        modifier = Modifier.padding(start = 60.dp),
                        style = TextStyle(fontSize = 13.sp)
                    )
                    Button(
                        onClick = {
                            val intent = Intent(context, Login::class.java)
                            context.startActivity(intent)

                        }, modifier = Modifier
                            .padding(start = 8.dp)
                            .scale(0.8f)

                    ) {
                        Text("LOGIN")
                    }
                }


            }

        }
    })
}


@Preview(showSystemUi = true)
@Composable
fun show_3() {
    Signup_page()
}