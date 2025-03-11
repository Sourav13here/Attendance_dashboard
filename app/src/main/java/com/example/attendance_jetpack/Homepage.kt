@file:OptIn(ExperimentalFoundationApi::class, ExperimentalFoundationApi::class)

package com.example.attendance_jetpack

import android.content.Intent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box


import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.attendance_jetpack.Login.Login
import com.example.attendance_jetpack.ui.theme.Whitty


@ExperimentalFoundationApi
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home_screen() {
    val content= LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,

                    ) {
                        logo(R.drawable.logonew)
                    }
                },
                navigationIcon = {
                    button(icon = R.drawable.baseline_menu_24)
                },
                actions = {
                    IconButton(onClick = {
                        val intent = Intent(content, Login::class.java)
                        content.startActivity(intent)

                    }) {
                        Icon(painter = painterResource(id = R.drawable.profile),
                            contentDescription = "",
                            modifier = Modifier.size(32.dp))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White,
                    titleContentColor = Color.Black
                )

                

            )
        },
        bottomBar = {
            BottomAppBar (
                contentColor = Color.Black,
                containerColor = Color.White,
                modifier = Modifier
                    .height(80.dp)
            ){
                IconButton(onClick = {}, modifier = Modifier
                    .padding(start = 40.dp)) {
                    Icon(painter = painterResource(R.drawable.baseline_home_24),
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxSize())
                }
                IconButton(onClick = {},
                    modifier = Modifier
                        .padding(start=200.dp)) {
                    Icon(painter = painterResource(R.drawable.baseline_share_24),
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxSize()
                            )
                }

            }
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(paddingValues)
                    .padding(5.dp)

            ) {





           Box(modifier = Modifier
               .align(TopCenter)
               .zIndex(0.2f)
               .padding(top = 96.dp)
               .height(60.dp)
               .width(150.dp)
               .background(color= Color.Gray)
               .border(2.dp, color = Black)) {

       Text(
        text = "Events",
        color = Black,

        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier
            .align(Center)



        )
          }

                    Image(painter = painterResource(id=R.drawable.bvec),contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .blur(7.dp),
                        Alignment.TopCenter,
                        contentScale = ContentScale.FillBounds
                            )

                val itemList= listOf(
                    "Resplandor 6.0 starts from 24th Feb",
                    "When  female goats give birth to babies, called kids",
                    "The ladybug is the official insect of at least five states of the USA",
                    "On average, dogs have better eyesight than humans, although not as colourful",
                    "Dolphins sleep with one eye open",
                    "",
                    "",
                    "","","","","","",""
                )

      LazyColumn(
        modifier = Modifier
            .width(380.dp)
            .padding(top = 200.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
            .background(
                Whitty.copy(.8f)
                )
            ) {

        items(itemList) { itemText ->
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Gray),
                ) {
                Text(
                    text = itemText,
                    maxLines = 1,
                    overflow = TextOverflow.Visible,

                    modifier = Modifier
                        .padding(8.dp)
                        .height(30.dp)
                        .basicMarquee()


//
                )

              }
          }
       }

      }








        }

    )



}


@Composable
fun button(@DrawableRes icon: Int, tint: Color = Color.Unspecified) {
    IconButton(onClick = { /* TODO: Add functionality */ }) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = "Button Icon",
            tint = tint,
            modifier = Modifier.size(32.dp)
        )
    }
}

@Composable
fun logo(@DrawableRes imageRes: Int) {
    Image(
        painter = painterResource(id = imageRes),
        contentDescription = "Displayed Image",
        modifier = Modifier
            .height(56.dp)
    )
}







@Preview(showSystemUi = true)
@Composable
fun show() {
    Home_screen()
}
