package samyups.example.chessclock.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TimeSettings")

data class TimeSetting(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val timerA: String,
    val timerB: String,
    val delayA: Boolean = false,
    val delayATime: String = "5",
    val delayB: Boolean = false,
    val delayBTime: String = "5",
    val incrementA: Boolean = false,
    val incrementATime: String = "5",
    val incrementB: Boolean = false,
    val incrementBTime: String = "5"
    )