package com.example.a1c_reference_book

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.a1c_reference_book.navigation.NavGraph
import com.example.a1c_reference_book.ui.theme._1C_Reference_BookTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val app = application as HandbookApplication

        setContent {
            _1C_Reference_BookTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavGraph(
                        navController = navController,
                        database = app.database
                    )
                }
            }
        }
    }
}
