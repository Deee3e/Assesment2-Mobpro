package com.desijihanf607062300071.assesment2.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.desijihanf607062300071.assesment2.R
import com.desijihanf607062300071.assesment2.util.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrashScreen(navController: NavHostController) {

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.sampah),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                } ,
                title = {
                    Text(text = stringResource(id = R.string.sampah))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        }
    ) { innerPadding ->
        TrashContent(Modifier.padding(innerPadding))
    }
}

@Composable
fun TrashContent(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val factory = ViewModelFactory(context)
    val viewModel: TrashViewModel = viewModel(factory = factory)

    val data by viewModel.data.collectAsState()

    if (data.isEmpty()) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(id = R.string.list_kosong))
        }
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 84.dp)
        ) {
            items(data) { movie ->
                Box(modifier = Modifier.fillMaxWidth()) {
                    ListItem(
                        movie = movie,
                        onClick = {  }
                    )

                    var expanded by remember { mutableStateOf(false) }

                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                    ) {
                        IconButton(onClick = { expanded = true }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "More")
                        }

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text(stringResource(R.string.pulihkan)) },
                                onClick = {
                                    expanded = false
                                    viewModel.pulihkan(movie.id)
                                }
                            )
                            DropdownMenuItem(
                                text = { Text(stringResource(R.string.hapus_permanen)) },
                                onClick = {
                                    expanded = false
                                    viewModel.hapuspermanen(movie.id)
                                }
                            )
                        }
                    }
                }
                HorizontalDivider()
            }
        }
    }
}