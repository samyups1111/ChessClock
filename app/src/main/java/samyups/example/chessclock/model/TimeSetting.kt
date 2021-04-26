package samyups.example.chessclock.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TimeSettings")

data class TimeSetting(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val timerA: String,
    val timerB: String,
    val delayA: Boolean = false,
    val delayB: Boolean = false,
    val incrementA: Boolean = false,
    val incrementB: Boolean = false
    )