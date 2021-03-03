package samyups.example.chessclock

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Time Settings")

data class TimeSettings(

    @PrimaryKey(autoGenerate = true) val id: Int,
    val timerA: String,
    val timerB: String
    )