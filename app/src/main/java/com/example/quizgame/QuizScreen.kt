package com.example.quizgame

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@Composable
fun QuizScreen(navController: NavController) {
    var questionIndex by remember { mutableStateOf(0) }
    var selectedAnswer by remember { mutableStateOf<String?>(null) }
    var selectedAnswers by remember { mutableStateOf<Set<String>>(emptySet()) }
    var showDialog by remember { mutableStateOf(false) }

    // Define questions and answers
    val questions = listOf(
        "What is the capital of France?" to listOf("Paris", "London", "Rome", "Berlin"),
        "Which are considered real planets?" to listOf("Chlorophyl", "Mars", "Jupiter", "Borax"),
        "What is the largest ocean on Earth?" to listOf("Atlantic Ocean", "Indian Ocean", "Arctic Ocean", "Pacific Ocean"),
        "Who wrote 'Romeo and Juliet'?" to listOf("Mark Twain", "William Shakespeare", "Charles Dickens", "Ernest Hemingway"),
        "What is necessary for Humans to live?" to listOf("Food", "Water", "CS", "Mobile Development")
    )
    val correctAnswers = listOf(
        setOf("Paris"),
        setOf("Mars","Jupiter"),
        setOf("Pacific Ocean"),
        setOf("William Shakespeare"),
        setOf("Food","Water")
    )

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Question ${questionIndex + 1}",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = questions[questionIndex].first,
            fontSize = 22.sp,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (questionIndex == 1 || questionIndex == 4) {
            questions[questionIndex].second.forEach { answer ->
                CheckboxWithLabel(
                    label = answer,
                    isSelected = selectedAnswers.contains(answer),
                    onSelect = { isSelected ->
                        selectedAnswers = if (isSelected) {
                            selectedAnswers + answer
                        } else {
                            selectedAnswers - answer
                        }
                    }
                )
            }
        } else {
            questions[questionIndex].second.forEach { answer ->
                RadioButtonWithLabel(
                    label = answer,
                    isSelected = selectedAnswer == answer,
                    onSelect = {
                        selectedAnswer = answer
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = { showDialog = true },
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
        ) {
            Text("Next", fontSize = 20.sp, color = Color.White)
        }
    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Are you sure?") },
            text = {
                Text("You have selected '${selectedAnswer ?: selectedAnswers.joinToString()}', is this your final answer?")
            },
            confirmButton = {
                Button(
                    onClick = {
                        scope.launch {
                            if (selectedAnswers == correctAnswers[questionIndex]) {
                                SharedPrefsManager.incrementScore(navController.context)
                            } else if (selectedAnswer == correctAnswers[questionIndex].first()) {
                                SharedPrefsManager.incrementScore(navController.context)
                            }
                            if (questionIndex < questions.size - 1) {
                                questionIndex++
                                selectedAnswer = null
                                selectedAnswers = emptySet()
                            } else {
                                navController.navigate("result")
                            }
                        }
                        showDialog = false
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDialog = false }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun RadioButtonWithLabel(label: String, isSelected: Boolean, onSelect: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        RadioButton(selected = isSelected, onClick = onSelect)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = label, fontSize = 18.sp, color = MaterialTheme.colorScheme.onSurface)
    }
}

@Composable
fun CheckboxWithLabel(label: String, isSelected: Boolean, onSelect: (Boolean) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Checkbox(checked = isSelected, onCheckedChange = { onSelect(it) })
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = label, fontSize = 18.sp, color = MaterialTheme.colorScheme.onSurface)
    }
}
