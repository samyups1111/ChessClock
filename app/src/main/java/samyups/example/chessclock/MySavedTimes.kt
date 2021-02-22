package samyups.example.chessclock

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Time Settings")

data class MySavedTimes(
    @PrimaryKey(autoGenerate = true)  val id: Int,
    @ColumnInfo(name = "name") val  name: String
    )