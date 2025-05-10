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
    import androidx.compose.material.icons.filled.Delete
    import androidx.compose.material3.*
    import androidx.compose.runtime.*
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
    import com.desijihanf607062300071.assesment2.R
    import com.desijihanf607062300071.assesment2.database.MovieDb
    import com.desijihanf607062300071.assesment2.model.Movie
    import com.desijihanf607062300071.assesment2.ui.theme.MyApplicationTheme
    import com.desijihanf607062300071.assesment2.util.SettingsDataStore
    import com.desijihanf607062300071.assesment2.util.ViewModelFactory
    import kotlinx.coroutines.CoroutineScope
    import kotlinx.coroutines.Dispatchers
    import kotlinx.coroutines.launch

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainScreen(navController: NavHostController) {
        val dataStore = SettingsDataStore(LocalContext.current)
        val showList by dataStore.layoutFlow.collectAsState(true)

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(id = R.string.app_name)) },
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
                        IconButton(onClick = { navController.navigate(Screen.TrashScreen.route) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Recycle Bin")
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
                        contentDescription = stringResource(R.string.tambah_movie),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        ) { innerPadding ->
            ScreenContent(showList, Modifier.padding(innerPadding), navController)
        }
    }

    @Composable
    fun ListItem(movie: Movie, onClick: () -> Unit) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable { onClick() },
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = movie.judul, fontWeight = FontWeight.Bold)
            Text(text = movie.waktu, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(text = movie.platform)
        }
    }

    @Composable
    fun GridItem(movie: Movie, onClick: () -> Unit) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() },
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
        ) {
            Column(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = movie.judul, fontWeight = FontWeight.Bold)
                Text(text = movie.waktu, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text(text = movie.platform)
            }
        }
    }

    @Composable
    fun ScreenContent(showList: Boolean, modifier: Modifier = Modifier, navController: NavHostController) {
        val context = LocalContext.current
        val factory = ViewModelFactory(context)
        val viewModel: MainViewModel = viewModel(factory = factory)

        val data by viewModel.data.collectAsState()

        if (data.isEmpty()) {
            Column(
                modifier = modifier.fillMaxSize().padding(16.dp),
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
                    items(data) { movie ->
                        ListItem(movie = movie) {
                            navController.navigate(Screen.FormUbah.createRoute(movie.id))
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
                    items(data) { movie ->
                        GridItem(movie = movie) {
                            navController.navigate(Screen.FormUbah.createRoute(movie.id))
                        }
                    }
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
    @Composable
    fun MainScreenPreview() {
        MyApplicationTheme {
            MainScreen(rememberNavController())
        }
    }
