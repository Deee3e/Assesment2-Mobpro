package com.desijihanf607062300071.assesment2.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.desijihanf607062300071.assesment2.database.CatatanDao
import com.desijihanf607062300071.assesment2.model.Catatan
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue


class FormViewModel(private val dao: CatatanDao) : ViewModel() {

    private val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)

    // 🔽 List kategori dropdown
    val kategoriList = listOf("Pribadi", "Pekerjaan", "Sekolah", "Lainnya")

    // 🔽 State pilihan kategori
    var selectedKategori by mutableStateOf(kategoriList.first())
        private set

    // 🔽 Fungsi untuk update pilihan kategori dari UI
    fun onKategoriSelected(kategori: String) {
        selectedKategori = kategori
    }

    fun insertCatatan(judul: String, isi: String, tanggal: String, kategori: String) {
        val currentDate = Date()
        val formattedDate = formatter.format(currentDate)

        val catatan = Catatan(
            judul = judul,
            isi = isi,
            tanggal = formattedDate,
            kategori = kategori
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(catatan)
        }
    }

    suspend fun getCatatanById(id: Int): Catatan? {
        return withContext(Dispatchers.IO) {
            dao.getById(id)
        }
    }

    fun updateCatatan(id: Int, judul: String, isi: String, tanggal: String, kategori: String) {
        val currentDate = Date()
        val formattedDate = formatter.format(currentDate)

        val updatedCatatan = Catatan(
            id = id,
            judul = judul,
            isi = isi,
            tanggal = formattedDate ,
            kategori = kategori
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.update(updatedCatatan)
        }
    }

    fun deleteCatatan(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.getById(id)?.let { catatan ->
                dao.delete(catatan)
            }
        }
    }
}
