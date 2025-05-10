package com.desijihanf607062300071.assesment2.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.desijihanf607062300071.assesment2.database.MovieDao
import com.desijihanf607062300071.assesment2.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(private val dao: MovieDao) : ViewModel() {

    fun insert(judul: String, waktu: String, platform: String) {
        val entry = Movie(
            judul = judul,
            waktu = waktu,
            platform = platform
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(entry)
        }
    }

    suspend fun getMovieById(id: Long): Movie? {
        return dao.getMovieById(id)
    }

    fun update(id: Long, judul: String, waktu: String, platform: String) {
        val entry = Movie(
            id = id,
            judul = judul,
            waktu = waktu,
            platform = platform
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.update(entry)
        }
    }

    fun softDelete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.softDelete(id)
        }
    }
}