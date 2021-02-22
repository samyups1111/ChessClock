package samyups.example.chessclock

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MySavedTimesDao {

    @Query("SELECT * FROM `time settings` ORDER BY id ASC")
    fun getIncrementId(): Flow<List<MySavedTimes>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(name : MySavedTimes)

    @Query("DELETE FROM `time settings`")
    suspend fun deleteALL()
}