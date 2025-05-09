package com.desijihanf607062300071.assesment2.ui.screen

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.desijihanf607062300071.assesment2.R
import com.desijihanf607062300071.assesment2.ui.theme.MyApplicationTheme
import com.desijihanf607062300071.assesment2.util.ViewModelFactory
import kotlinx.coroutines.launch
import com.desijihanf607062300071.assesment2.ui.screen.CategoryDropDown


const val KEY_ID_CATATAN = "id"

data class CatatanUndo(val judul: String, val isi: String, val tanggal: String, val kategori: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavHostController, catatanId: Int? = null) {
    val context = LocalContext.current
    val factory = ViewModelFactory(context)
    val viewModel: FormViewModel = viewModel(factory = factory)
    val coroutineScope = rememberCoroutineScope()

    var judul by remember { mutableStateOf("") }
    var isi by remember { mutableStateOf("") }
    val currentDate = remember {
        java.text.SimpleDateFormat("dd-MM-yyyy", java.util.Locale.getDefault()).format(java.util.Date())
    }
    var kategori by remember { mutableStateOf("Pribadi") }
    val daftarKategori = listOf("Pribadi", "Pekerjaan", "Penting", "Lainnya")
    var showDialog by remember { mutableStateOf(false) }
    var recentlyDeleted by remember { mutableStateOf<CatatanUndo?>(null) }
    var showUndo by remember { mutableStateOf(false) }

    LaunchedEffect(catatanId) {
        if (catatanId == null) return@LaunchedEffect
        viewModel.getCatatanById(catatanId)?.let { data ->
            judul = data.judul
            isi = data.isi
            kategori = data.kategori
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = {
                    Text(text = if (catatanId == null) stringResource(R.string.tambah_catatan) else stringResource(R.string.edit_catatan))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    IconButton(onClick = {
                        if (judul.isBlank() || isi.isBlank()) {
                            Toast.makeText(context, R.string.invalid, Toast.LENGTH_SHORT).show()
                            return@IconButton
                        }
                        coroutineScope.launch {
                            if (catatanId == null) {
                                viewModel.insertCatatan(judul, isi, currentDate, kategori)
                            } else {
                                viewModel.updateCatatan(catatanId, judul, isi, currentDate, kategori)
                            }
                            navController.popBackStack()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = stringResource(R.string.simpan),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) { padding ->

        FormCatatan(
            title = judul,
            onTitleChange = { judul = it },
            desc = isi,
            onDescChange = { isi = it },
            kategori = kategori,
            onKategoriChange = { kategori = it },
            modifier = Modifier.padding(padding)
        )

        if (catatanId != null && showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text(text = "Delete Catatan") },
                text = { Text(text = "Are you sure you want to delete this note?") },
                confirmButton = {
                    Button(onClick = {
                        recentlyDeleted = CatatanUndo(judul, isi, currentDate, kategori)
                        showUndo = true
                        coroutineScope.launch {
                            viewModel.deleteCatatan(catatanId)
                            navController.popBackStack()
                        }
                        showDialog = false
                    }) {
                        Text("Delete")
                    }
                },
                dismissButton = {
                    Button(onClick = { showDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }

        if (showUndo && recentlyDeleted != null) {
            Snackbar(
                action = {
                    Button(onClick = {
                        coroutineScope.launch {
                            viewModel.insertCatatan(
                                recentlyDeleted!!.judul,
                                recentlyDeleted!!.isi,
                                recentlyDeleted!!.tanggal,
                                recentlyDeleted!!.kategori
                            )
                        }
                    }) {
                        Text("Undo")
                    }
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Catatan dihapus")
            }
        }
    }
}

@Composable
fun FormCatatan(
    title: String,
    onTitleChange: (String) -> Unit,
    desc: String,
    onDescChange: (String) -> Unit,
    kategori: String,
    onKategoriChange: (String) -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = onTitleChange,
            label = { Text(text = stringResource(R.string.judul)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = desc,
            onValueChange = onDescChange,
            label = { Text(text = stringResource(R.string.isi_catatan)) },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences
            ),
            modifier = Modifier.fillMaxSize()
        )

        // 🔽 Ganti dropdown lama dengan dropdown baru
        CategoryDropDown(
            selectedCategory = kategori,
            onCategorySelected = onKategoriChange
        )
    }
}



@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun FormScreenPreview() {
    MyApplicationTheme {
        DetailScreen(rememberNavController())
    }
}
