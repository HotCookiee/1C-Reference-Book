package com.example.a1c_reference_book

import android.app.Application
import com.example.a1c_reference_book.database.AppDatabase
import com.example.a1c_reference_book.utils.DatabaseSeeder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HandbookApplication : Application() {

    val database: AppDatabase by lazy {
        AppDatabase.getDatabase(this)
    }

    override fun onCreate() {
        super.onCreate()

        CoroutineScope(Dispatchers.IO).launch {
            val seeder = DatabaseSeeder()
            seeder.seedDatabase(database)
        }
    }
}
