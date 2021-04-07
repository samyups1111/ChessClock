package samyups.example.chessclock.utils

import android.app.Application
import samyups.example.chessclock.model.MainRepository
import samyups.example.chessclock.model.MainRoomDatabase

class MainApplication : Application() {
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    private val roomDatabase by lazy { MainRoomDatabase.getInstance(this) }
    val repository by lazy { MainRepository(roomDatabase.timeSettingsDao()) }
}