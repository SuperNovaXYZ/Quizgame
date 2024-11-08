package com.example.quizgame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.quizgame.ui.theme.QuizgameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuizgameTheme{
            Surface(color = MaterialTheme.colorScheme.background) {
                val navController = rememberNavController()
                QuizNavGraph(navController = navController)
            }
            }
        }
    }
}
