package com.desijihanf607062300071.assesment2.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.desijihanf607062300071.assesment2.database.CatatanDb
import com.desijihanf607062300071.assesment2.ui.screen.FormViewModel
import com.desijihanf607062300071.assesment2.ui.screen.ListViewModel

class ViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val dao = CatatanDb.getInstance(context).catatanDao()
        if (modelClass.isAssignableFrom(ListViewModel::class.java)) {
            return ListViewModel(dao) as T
        } else if (modelClass.isAssignableFrom(FormViewModel::class.java)) {
            return FormViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
