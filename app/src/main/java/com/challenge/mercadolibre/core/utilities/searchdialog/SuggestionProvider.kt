package com.challenge.mercadolibre.core.utilities.searchdialog

import android.content.SearchRecentSuggestionsProvider

class SuggestionProvider : SearchRecentSuggestionsProvider() {

    companion object {
        const val AUTHORITY = "com.challenge.mercadolibre.core.utilities.searchdialog.SuggestionProvider"
        const val MODE: Int = DATABASE_MODE_QUERIES
    }

    init {
        setupSuggestions(AUTHORITY, MODE)
    }

}