package com.desijihanf607062300071.assesment2.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.desijihanf607062300071.assesment2.database.MovieDb
import com.desijihanf607062300071.assesment2.ui.screen.DetailViewModel
import com.desijihanf607062300071.assesment2.ui.screen.MainViewModel
import com.desijihanf607062300071.assesment2.ui.screen.TrashViewModel

class ViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val dao = MovieDb.getInstance(context).dao
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(dao) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(dao) as T
        } else if (modelClass.isAssignableFrom(TrashViewModel::class.java)) {
            return TrashViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
