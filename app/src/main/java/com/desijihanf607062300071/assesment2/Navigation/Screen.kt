import com.desijihanf607062300071.assesment2.Navigation.KEY_MOVIE_ID

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object FormBaru : Screen("formMovieBaru")
    object FormUbah : Screen("formMovieUbah/{$KEY_MOVIE_ID}") {
        fun createRoute(id: Long) = "formMovieUbah/$id"
    }
    object TrashScreen : Screen("recycleBin")
}
