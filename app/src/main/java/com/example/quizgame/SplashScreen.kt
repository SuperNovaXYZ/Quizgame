package com.example.quizgame

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun SplashScreen(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = painterResource(id = R.drawable.app_logo), contentDescription = null)
            Spacer(modifier = Modifier.height(16.dp))
            Text("Nova's Quiz Game", fontSize = 32.sp, style = MaterialTheme.typography.headlineLarge)

        }
    }


    Handler(Looper.getMainLooper()).postDelayed({
        navController.navigate("login") {
            popUpTo("splash") { inclusive = true }
        }
    }, 3000)
}
