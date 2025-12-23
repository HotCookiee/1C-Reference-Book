package com.example.a1c_reference_book

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.example.a1c_reference_book.database.AppDatabase
import com.example.a1c_reference_book.navigation.NavGraph
import com.example.a1c_reference_book.ui.theme.A1cReferenceBookTheme
import com.example.a1c_reference_book.viewmodels.ThemeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = AppDatabase.getDatabase(applicationContext)


        setContent {
            val themeViewModel = remember { ThemeViewModel(applicationContext) }
            val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()

            A1cReferenceBookTheme(darkTheme = isDarkTheme) {
                val navController = rememberNavController()
                NavGraph(
                    navController = navController,
                    database = database,
                    themeViewModel = themeViewModel
                )
            }
        }
    }
}
