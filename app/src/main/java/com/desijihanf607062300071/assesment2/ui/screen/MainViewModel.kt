package com.desijihanf607062300071.assesment2.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.desijihanf607062300071.assesment2.database.MovieDao
import com.desijihanf607062300071.assesment2.model.Movie
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class MainViewModel(dao: MovieDao) : ViewModel() {
    val data: StateFlow<List<Movie>> = dao.getMovies().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
}