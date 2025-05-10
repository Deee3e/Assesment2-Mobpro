package com.desijihanf607062300071.assesment2.database

import androidx.room.*
import com.desijihanf607062300071.assesment2.model.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert suspend fun insert(movie: Movie)
    @Update suspend fun update(movie: Movie)

    @Query("SELECT * FROM movie WHERE isDeleted = 0 ORDER BY id ASC")
    fun getMovies(): Flow<List<Movie>>

    @Query("SELECT * FROM movie WHERE isDeleted = 1 ORDER BY id ASC")
    fun getDeletedMovies(): Flow<List<Movie>>

    @Query("SELECT * FROM movie WHERE id = :id")
    suspend fun getMovieById(id: Long): Movie?

    @Query("DELETE FROM movie WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("UPDATE movie SET isDeleted = 1 WHERE id = :id")
    suspend fun softDelete(id: Long)

    @Query("UPDATE movie SET isDeleted = 0 WHERE id = :id")
    suspend fun restore(id: Long)
}