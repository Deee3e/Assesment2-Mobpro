package com.desijihanf607062300071.assesment2.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "catatan")
data class Catatan(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val judul: String,
    val isi: String,
    val tanggal: String,
    val kategori: String,
    val isDeleted: Boolean = false
)

