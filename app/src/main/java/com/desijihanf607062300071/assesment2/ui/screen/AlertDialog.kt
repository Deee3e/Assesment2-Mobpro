package com.desijihanf607062300071.assesment2.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.desijihanf607062300071.assesment2.R


@Composable
fun DeleteCatatanDialog(
    selectedCategory: String,
    onCategorySelected: (String) -> Unit,
    onDismissRequest: () -> Unit,
    onConfirmDelete: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(text = stringResource(R.string.hapus_catatan))
        },
        text = {
            Column {
                Text(text = stringResource(R.string.pesan_hapus))
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = stringResource(R.string.pilih_kategori))
                CategoryDropDown(
                    selectedCategory = selectedCategory,
                    onCategorySelected = onCategorySelected
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirmDelete) {
                Text(stringResource(R.string.hapus))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(stringResource(R.string.batal))
            }
        }
    )
}
