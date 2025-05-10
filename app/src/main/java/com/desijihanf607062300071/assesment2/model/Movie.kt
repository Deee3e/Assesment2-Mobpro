package com.desijihanf607062300071.assesment2.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class Movie(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val judul: String,
    val waktu: String,
    val platform: String,
    val isDeleted: Boolean = false
)