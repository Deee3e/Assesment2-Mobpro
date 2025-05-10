package com.desijihanf607062300071.assesment2.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.desijihanf607062300071.assesment2.database.MovieDao
import com.desijihanf607062300071.assesment2.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TrashViewModel (private val dao: MovieDao) : ViewModel() {

    val data: StateFlow<List<Movie>> = dao.getDeletedMovies().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )

    fun pulihkan(id: Long) {
        viewModelScope.launch (Dispatchers.IO){
            dao.restore(id)
        }
    }

    fun hapuspermanen(id: Long) {
        viewModelScope.launch (Dispatchers.IO){
            dao.deleteById(id)
        }
    }
}