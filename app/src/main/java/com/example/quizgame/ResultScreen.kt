package com.example.quizgame

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@Composable
fun ResultScreen(navController: NavController) {
    val context = LocalContext.current
    val score by SharedPrefsManager.getScore(context).collectAsState(initial = 0)
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Quiz Finished!", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Your Score: $score", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            coroutineScope.launch {
                SharedPrefsManager.saveGameHistory(context, score)
                SharedPrefsManager.resetScore(context)
                navController.popBackStack("login", inclusive = false)
            }
        }) {
            Text("Go Back to Home")
        }
        Button(onClick = {
            coroutineScope.launch {
                SharedPrefsManager.saveGameHistory(context, score)
                SharedPrefsManager.resetScore(context)
                navController.navigate("history")
            }
        }) {
            Text("View Game History")
        }
    }
}
