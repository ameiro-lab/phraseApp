package com.renat.phrasecollection.ui.navigation

/**
 * Navigation destinations used by the app.
 */
sealed class Screen(val route: String) {
    /** Phrase list screen route. */
    data object List : Screen("list")

    /** Card browsing screen route. */
    data object Card : Screen("card")

    /** New phrase registration screen route shown as a bottom tab. */
    data object Register : Screen("register")

    /** Settings screen route. */
    data object Setting : Screen("setting")

    /** Edit screen route with an optional phrase id encoded as -1 for new phrases. */
    data object Edit : Screen("edit/{phraseId}") {
        /** Builds a route for creating a new phrase. */
        fun newRoute(): String = "edit/-1"

        /** Builds a route for editing an existing phrase. */
        fun editRoute(phraseId: Long): String = "edit/$phraseId"
    }
}
