package com.desijihanf607062300071.assesment2.Navigation

const val KEY_ID_CATATAN = "id" // Corrected constant name to match use in composable

sealed class Screen(val route: String) {
    object Home : Screen("home") // Changed from "mainScreen" to "home" to be more general and consistent
    object FormBaru : Screen("form_baru") // Changed from "detailScreen" to "form_baru"
    object FormUbah : Screen("form_ubah/{$KEY_ID_CATATAN}") { // Corrected route to match NavGraph
        fun withId(id: Int): String { // Changed id type to Int to be consistent
            return "form_ubah/$id" // Corrected route to match NavGraph
        }
    }
    object RecycleBin : Screen("recycle_bin") // Added route for Recycle Bin
}
