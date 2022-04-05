package com.example.pepflix.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pepflix.dao.TVShowDao
import com.example.pepflix.models.TVShow

@Database(entities = [TVShow::class], version = 1, exportSchema = false)
abstract class TVShowsDatabase : RoomDatabase() {

    abstract fun tvShowsDao() : TVShowDao

    companion object {
        @Volatile
        private var INSTANCE: TVShowsDatabase? = null

        fun getDatabase(context: Context) : TVShowsDatabase {
            val tempInstance = INSTANCE

            if (tempInstance != null) {
                return tempInstance
            } else {
                synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        TVShowsDatabase::class.java,
                        "tv_shows_database"
                    ).build()
                    INSTANCE = instance
                    return instance
                }
            }
        }
    }
}