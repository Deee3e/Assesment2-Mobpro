package com.desijihanf607062300071.assesment2.database

import android.content.Context
import androidx.room.*
import com.desijihanf607062300071.assesment2.model.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class MovieDb : RoomDatabase() {

    abstract val dao: MovieDao

    companion object {
        @Volatile
        private var INSTANCE: MovieDb? = null

        fun getInstance(context: Context): MovieDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDb::class.java,
                    "movie.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}