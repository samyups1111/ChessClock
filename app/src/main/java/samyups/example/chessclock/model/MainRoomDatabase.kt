package samyups.example.chessclock.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [TimeSetting::class], version = 3, exportSchema = false)

abstract class MainRoomDatabase : RoomDatabase() {

    abstract fun timeSettingsDao() : MainDao



    companion object {
        //Singleton prevents multiple instances of database opening at the
        //same time.
        @Volatile
        private var INSTANCE: MainRoomDatabase? = null

        fun getInstance(context: Context): MainRoomDatabase {
            //if the INSTANCE is not null, then return it,
            //if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MainRoomDatabase::class.java,
                    "mySavedTimes_Database"
                )
                        .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

