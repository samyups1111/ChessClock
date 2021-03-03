package samyups.example.chessclock

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TimeSettingsDao {

    @Query("SELECT * FROM `time settings` ORDER BY id ASC")
    fun getIncrementId(): Flow<List<TimeSettings>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(timeSettings : TimeSettings)

    @Query("DELETE FROM `time settings`")
    suspend fun deleteALL()
}