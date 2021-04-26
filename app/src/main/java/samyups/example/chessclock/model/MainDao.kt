package samyups.example.chessclock.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MainDao {

    @Query("SELECT * FROM `TimeSettings` ORDER BY id ASC")
    fun getTimeSettings(): Flow<List<TimeSetting>>

    @Query("SELECT * FROM `TimeSettings` WHERE id = :id")
    fun getTimeSetting(id: Int ): Flow<TimeSetting>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(timeSetting : TimeSetting)

    @Query("DELETE FROM `TimeSettings`")
    suspend fun deleteALL()

    @Delete
    suspend fun delete(timeSetting: TimeSetting)

    @Update
    suspend fun update(timeSetting: TimeSetting)
}