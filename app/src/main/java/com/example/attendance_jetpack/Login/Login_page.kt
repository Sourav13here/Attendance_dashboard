@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.attendance_jetpack.Login

import android.annotation.SuppressLint
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.attendance_jetpack.Login.firebase.login_handler
import com.example.attendance_jetpack.MainActivity
import com.example.attendance_jetpack.R
import com.example.attendance_jetpack.Signup.Signup

@ExperimentalMaterial3Api
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable

fun Login_page(){

    var password by remember { mutableStateOf("") }
    var userType by remember { mutableStateOf("Student") }
    var studentId by remember { mutableStateOf("") }
    var teacherId by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
     val content= LocalContext.current

    Scaffold (
        topBar = {
            TopAppBar(
                title =
                {
                    Row (
                        modifier= Modifier
                            .fillMaxWidth()
                            .padding(start = 110.dp),

                    )


                    {
                        Text(
                            text = "LOGIN", modifier = Modifier
                                .wrapContentSize(),
                            style = TextStyle(fontSize = 24.sp, fontStyle = FontStyle.Italic)
                        )

                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        val intent= Intent(content, MainActivity::class.java)
                        content.startActivity(intent)


                    } ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_arrow_back_24),
                            contentDescription = "",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            )
        },
      content = {
        paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
                .padding(20.dp)

        )
        {
            Box (modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 60.dp)) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(R.drawable.logonew), contentDescription = "",
                        modifier = Modifier.size(100.dp)
                    )


                    Spacer(modifier = Modifier.size(30.dp))


                    Row (horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 47.dp)){
                        Text(text="User:", fontSize = 19.sp)

                        Spacer (modifier = Modifier.size(10.dp))

                        Row (verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable { userType=" " }){
                            RadioButton(
                                selected = userType=="Student",
                                onClick = {userType="Student"}
                            )
                            Text("Student")
                        }

                        Spacer(modifier = Modifier.size(20.dp))

                        Row (
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable { userType="Teacher" }

                        ){
                            RadioButton(selected = userType=="Teacher",
                                        onClick = {userType="Teacher"})
                            Text("Teacher")
                        }
                    }
                    Spacer(modifier = Modifier.size(15.dp))

                    if (userType=="Student"){
                        OutlinedTextField(
                            value = studentId,
                            onValueChange = {studentId=it},
                            label = { Text("ASTU Roll no") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()

                        )
                    }
                    else {
                        OutlinedTextField(
                            value = teacherId,
                            onValueChange = { teacherId = it },
                            label = { Text("Teacher ID") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Spacer(modifier = Modifier.size(30.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        visualTransformation = if (passwordVisible){
                            VisualTransformation.None
                        }
                        else{
                            PasswordVisualTransformation()
                        },
                        trailingIcon = {
                            IconButton(onClick = {
                                passwordVisible=!passwordVisible
                            }) {
                                Icon(painter = painterResource(if
                                (passwordVisible) R.drawable.baseline_visibility_24
                                else
                                    R.drawable.baseline_visibility_off_24,

                                ),
                                    contentDescription = ""

                                )
                            }
                        }



                    )
                    Spacer(modifier = Modifier.size(18.dp))

                    Button(onClick = {
                        login_handler.handleLogin(
                            context = content,
                            userType = userType,
                            studentId = studentId,
                            teacherId = teacherId,
                            password = password
                        )
                    }) {
                        Text("LOGIN")

                    }
                    
                    Spacer(modifier = Modifier.size(120.dp))

                    Row (verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()){
                        Text("Don't have an account", modifier = Modifier.padding(start = 60.dp),
                            style =TextStyle(fontSize = 13.sp) )
                        Button(onClick = {
                            val intent= Intent(content, Signup::class.java)
                            content.startActivity(intent)

                        }, modifier = Modifier
                            .padding(start = 8.dp)
                            .scale(0.8f)

                        ) {
                            Text("SIGNUP")
                        }
                    }







                }
            }
        }
     }

    )


}
@Preview(showSystemUi = true)
@Composable
fun show_2(){
    Login_page()
}
