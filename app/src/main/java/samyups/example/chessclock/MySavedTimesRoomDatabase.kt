package samyups.example.chessclock

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MySavedTimes::class], version = 1, exportSchema = false)

abstract class MySavedTimesRoomDatabase : RoomDatabase() {

    abstract fun mySavedTimesDao() : MySavedTimesDao

    companion object {
        //Singleton prevents multiple instances of database opening at the
        //same time.
        @Volatile
        private var INSTANCE: MySavedTimesRoomDatabase? = null

        fun getDatabase(context: Context): MySavedTimesRoomDatabase {
            //if the INSTANCE is not null, then return it,
            //if it is, then create the databse
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MySavedTimesRoomDatabase::class.java,
                    "Time Settings"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}