package samyups.example.chessclock.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MainDao {

    @Query("SELECT * FROM `time settings` ORDER BY id ASC")
    fun getTimeSettings(): Flow<List<TimeSetting>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(timeSetting : TimeSetting)

    @Query("DELETE FROM `time settings`")
    suspend fun deleteALL()

    @Delete
    suspend fun delete(timeSetting: TimeSetting)

    @Update
    suspend fun update(timeSetting: TimeSetting)
}