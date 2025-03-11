package com.example.attendance_jetpack.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = White,
    secondary = White,
    tertiary = White

)

private val LightColorScheme = lightColorScheme(
    primary = Bvec,
    secondary =Color.Blue,
    tertiary = Color.Yellow

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun Attendance_JetpackTheme(
    darkTheme: Boolean = false,

    content: @Composable () -> Unit
) {
   val color=if (darkTheme){
       DarkColorScheme
    }
    else{
        LightColorScheme
   }


    MaterialTheme(
        colorScheme = color,
        typography = Typography,
        content = content
    )
}