package com.desijihanf607062300071.assesment2.ui.screen

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.desijihanf607062300071.assesment2.R
import com.desijihanf607062300071.assesment2.database.MovieDb
import com.desijihanf607062300071.assesment2.ui.theme.MyApplicationTheme
import com.desijihanf607062300071.assesment2.util.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavHostController, id: Long? = null) {
    val context = LocalContext.current
    val db = MovieDb.getInstance(context)
    val factory = ViewModelFactory(context)
    val viewModel: DetailViewModel = viewModel(factory = factory)

    var title by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var platform by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(id) {
        if (id == null) return@LaunchedEffect
        val data = viewModel.getMovieById(id) ?: return@LaunchedEffect
        title = data.judul
        time = data.waktu
        platform = data.platform
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
                    Text(
                        text = if (id == null) "Tambah Film" else "Edit Film"
                    )
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    IconButton(onClick = {
                        if (title.isEmpty() || time.isEmpty() || platform.isEmpty()) {
                            Toast.makeText(context, "Semua data wajib diisi!", Toast.LENGTH_SHORT).show()
                            return@IconButton
                        }

                        if (id == null) {
                            viewModel.insert(title, time, platform)
                        } else {
                            viewModel.update(id, title, time, platform)
                        }
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = stringResource(R.string.simpan),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    if (id != null) {
                        DeleteAction {
                            showDialog = true
                        }
                    }
                }
            )
        }
    ) { padding ->
        FormMovie(
            title = title,
            onTitleChange = { title = it },
            time = time,
            onTimeChange = { time = it },
            platform = platform,
            onPlatformChange = { platform = it },
            modifier = Modifier.padding(padding)
        )
    }

    if (id != null && showDialog) {
        DisplayAlertDialog(
            onDismissRequest = { showDialog = false }) {
            showDialog = false
            viewModel.softDelete(id)
            navController.popBackStack()
        }
    }
}

@Composable
fun DeleteAction(delete: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = { expanded = true }) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = stringResource(R.string.lainnya),
            tint = MaterialTheme.colorScheme.primary
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = {
                    Text(text = stringResource(id = R.string.hapus))
                },
                onClick = {
                    expanded = false
                    delete()
                }
            )
        }
    }
}

@Composable
fun FormMovie(
    title: String, onTitleChange: (String) -> Unit,
    time: String, onTimeChange: (String) -> Unit,
    platform: String, onPlatformChange: (String) -> Unit,
    modifier: Modifier
) {
    val timeList = listOf("Pagi", "Siang", "Sore", "Malam")
    val platformList = listOf("Netflix", "YouTube", "Disney+", "Hotstar")
    var dropdownExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = onTitleChange,
            label = { Text("Judul Film") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Text("Waktu Menonton:", style = MaterialTheme.typography.bodyMedium)
        Column {
            timeList.forEach { item ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onTimeChange(item) }
                        .padding(vertical = 4.dp)
                ) {
                    RadioButton(
                        selected = time == item,
                        onClick = { onTimeChange(item) }
                    )
                    Text(text = item, modifier = Modifier.padding(start = 8.dp))
                }
            }
        }

        Text("Platform:", style = MaterialTheme.typography.bodyMedium)
        Box {
            OutlinedButton(
                onClick = { dropdownExpanded = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (platform.isEmpty()) "Pilih Platform" else platform)
            }
            DropdownMenu(
                expanded = dropdownExpanded,
                onDismissRequest = { dropdownExpanded = false }
            ) {
                platformList.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(item) },
                        onClick = {
                            onPlatformChange(item)
                            dropdownExpanded = false
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DetailMovieScreenPreview() {
    MyApplicationTheme {
        DetailScreen(rememberNavController())
    }
}