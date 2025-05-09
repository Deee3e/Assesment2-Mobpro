package com.desijihanf607062300071.assesment2.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.desijihanf607062300071.assesment2.model.Catatan
import kotlinx.coroutines.flow.Flow

@Dao
interface CatatanDao {

    @Insert
    suspend fun insert(catatan: Catatan)

    @Update
    suspend fun update(catatan: Catatan)

    // Soft delete → hanya ubah isDeleted ke true
    @Query("UPDATE catatan SET isDeleted = 1 WHERE id = :id")
    suspend fun softDelete(id: Int)

    // Hard delete (permanen) jika memang dibutuhkan
    @Delete
    suspend fun delete(catatan: Catatan)

    // Tampilkan hanya catatan yang tidak dihapus
    @Query("SELECT * FROM catatan WHERE isDeleted = 0")
    fun getAll(): Flow<List<Catatan>>

    @Query("SELECT * FROM catatan WHERE id = :id")
    suspend fun getById(id: Int): Catatan?
}