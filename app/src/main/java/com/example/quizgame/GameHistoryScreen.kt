package com.example.quizgame

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun GameHistoryScreen(navController: NavController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var gameHistory by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        scope.launch {
            SharedPrefsManager.getGameHistory(context).collectLatest { history ->
                Log.d("GameHistory", "Game history fetched: $history")

                gameHistory = history
            }
        }
    }


    val historyEntries = gameHistory.split("|").filter { it.isNotBlank() }.takeLast(3)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text("Game History", fontSize = 24.sp)


        if (historyEntries.isEmpty()) {
            Text("No game history available.", fontSize = 18.sp)
        } else {
            historyEntries.forEachIndexed { index, score ->
                Text("Game ${historyEntries.size - index}: Score = $score", fontSize = 18.sp)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))


        Button(onClick = { navController.popBackStack() }) {
            Text("Back to Results")
        }
    }
}
