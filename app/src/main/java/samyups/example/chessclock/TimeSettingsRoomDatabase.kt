package samyups.example.chessclock

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [TimeSettings::class], version = 1, exportSchema = false)

abstract class TimeSettingsRoomDatabase : RoomDatabase() {

    abstract fun timeSettingsDao() : TimeSettingsDao

    companion object {
        //Singleton prevents multiple instances of database opening at the
        //same time.
        @Volatile
        private var INSTANCE: TimeSettingsRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): TimeSettingsRoomDatabase {
            //if the INSTANCE is not null, then return it,
            //if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TimeSettingsRoomDatabase::class.java,
                    "mySavedTimes_Database"
                )
                    .addCallback(TimeSettingsDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class TimeSettingsDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {

            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var timeSettingsDao = database.timeSettingsDao()

                    // Delete all content here.
                    timeSettingsDao.deleteALL()

                    //Add sample times.
                    var time1 = TimeSettings(1, "2", "2")
                    timeSettingsDao.insert(time1)
                    var time2 = TimeSettings(2,"3", "4")
                    timeSettingsDao.insert(time2)
                }
            }
        }
    }
}

