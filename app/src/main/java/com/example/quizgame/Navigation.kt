package com.example.quizgame

import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun QuizNavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }
        composable("rules") { RulesScreen(navController) }
        composable("quiz") { QuizScreen(navController) }
        composable("result") { ResultScreen(navController) }
        composable("history") { GameHistoryScreen(navController) }
    }
}
