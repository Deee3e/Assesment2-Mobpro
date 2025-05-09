package com.desijihanf607062300071.assesment2.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.desijihanf607062300071.assesment2.database.CatatanDao
import com.desijihanf607062300071.assesment2.model.Catatan
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class ListViewModel(private val dao: CatatanDao) : ViewModel() {
    val allCatatans: StateFlow<List<Catatan>> = dao.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}
