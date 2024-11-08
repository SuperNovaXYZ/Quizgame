package com.example.quizgame

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import java.io.IOException

object SharedPrefsManager {

    private const val PREFS_NAME = "quiz_prefs"
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFS_NAME)

    private val KEY_EMAIL = stringPreferencesKey("email")
    private val KEY_PASSWORD = stringPreferencesKey("password")
    private val KEY_SCORE = intPreferencesKey("score")
    private val KEY_GAME_HISTORY = stringPreferencesKey("game_history")

    suspend fun saveUserCredentials(context: Context, email: String, password: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_EMAIL] = email
            preferences[KEY_PASSWORD] = password
        }
    }

    fun getUserEmail(context: Context): Flow<String?> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences())
            else throw exception
        }
        .map { preferences ->
            preferences[KEY_EMAIL]
        }

    fun getUserPassword(context: Context): Flow<String?> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences())
            else throw exception
        }
        .map { preferences ->
            preferences[KEY_PASSWORD]
        }

    suspend fun incrementScore(context: Context) {
        context.dataStore.edit { preferences ->
            val currentScore = preferences[KEY_SCORE] ?: 0
            preferences[KEY_SCORE] = currentScore + 1
        }
    }

    fun getScore(context: Context): Flow<Int> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences())
            else throw exception
        }
        .map { preferences ->
            preferences[KEY_SCORE] ?: 0
        }

    suspend fun resetScore(context: Context) {
        context.dataStore.edit { preferences ->
            preferences[KEY_SCORE] = 0
        }
    }

    suspend fun saveGameHistory(context: Context, score: Int) {
        context.dataStore.edit { preferences ->
            val currentHistory = preferences[KEY_GAME_HISTORY] ?: ""
            val newHistory = if (currentHistory.isEmpty()) score.toString() else "$currentHistory|$score"
            preferences[KEY_GAME_HISTORY] = newHistory
            Log.d("GameHistory", "New history saved: $newHistory")

        }
    }

    fun getGameHistory(context: Context): Flow<String> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences())
            else throw exception
        }
        .map { preferences ->
            preferences[KEY_GAME_HISTORY] ?: ""
        }

}
