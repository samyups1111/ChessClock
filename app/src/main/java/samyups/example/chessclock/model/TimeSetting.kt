package samyups.example.chessclock.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Time Settings")

data class TimeSetting(

    @PrimaryKey(autoGenerate = true) val id: Int,
    val timerA: String,
    val timerB: String
    )