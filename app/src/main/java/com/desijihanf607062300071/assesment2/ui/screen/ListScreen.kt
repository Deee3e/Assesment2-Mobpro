package com.desijihanf607062300071.assesment2.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.desijihanf607062300071.assesment2.Navigation.Screen
import com.desijihanf607062300071.assesment2.model.Catatan
import com.desijihanf607062300071.assesment2.ui.theme.MyApplicationTheme
import com.desijihanf607062300071.assesment2.util.SettingDataStore
import com.desijihanf607062300071.assesment2.util.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.desijihanf607062300071.assesment2.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(navController: NavHostController) {
    val context = LocalContext.current
    val factory = ViewModelFactory(context)
    val viewModel: ListViewModel = viewModel(factory = factory)
    val data by viewModel.allCatatans.collectAsState(emptyList())
    val dataStore = SettingDataStore(context)
    val showList by dataStore.layoutFlow.collectAsState(true)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    IconButton(onClick = {
                        CoroutineScope(Dispatchers.IO).launch {
                            dataStore.saveLayout(!showList)
                        }
                    }) {
                        Icon(
                            painter = painterResource(
                                if (showList) R.drawable.baseline_grid_view_24
                                else R.drawable.baseline_view_list_24
                            ),
                            contentDescription = stringResource(
                                if (showList) R.string.grid
                                else R.string.list
                            ),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.FormBaru.route)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(R.string.tambah_catatan),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    ) { innerPadding ->
        ScreenContent(showList, Modifier.padding(innerPadding), navController, data)
    }
}

@Composable
fun ScreenContent(showList: Boolean, modifier: Modifier, navController: NavHostController, data: List<Catatan>) {
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
        if (showList) {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 84.dp)
            ) {
                items(data) { catatan ->
                    ListItem(catatan = catatan) {
                        navController.navigate(Screen.FormUbah.withId(catatan.id))
                    }
                    HorizontalDivider()
                }
            }
        } else {
            LazyVerticalStaggeredGrid(
                modifier = modifier.fillMaxSize(),
                columns = StaggeredGridCells.Fixed(2),
                verticalItemSpacing = 8.dp,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(8.dp, 8.dp, 8.dp, 84.dp)
            ) {
                items(data) { catatan ->
                    GridItem(catatan = catatan) {
                        navController.navigate(Screen.FormUbah.withId(catatan.id))
                    }
                }
            }
        }
    }
}

@Composable
fun ListItem(catatan: Catatan, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .clickable { onClick() }
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = catatan.judul,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = catatan.isi,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Text(text = catatan.tanggal)
    }
}


@Composable
fun GridItem(catatan: Catatan, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        border = BorderStroke(1.dp, DividerDefaults.color)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = catatan.judul,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = catatan.isi,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )
            Text(text = catatan.tanggal)
        }
    }
}


@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun ListScreenPreview() {
    MyApplicationTheme {
        ListScreen(rememberNavController())
    }
}
