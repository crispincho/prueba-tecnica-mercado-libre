package com.challenge.mercadolibre.core.utilities

import android.content.SharedPreferences

const val PREFERENCE_NAME = "Site"

private const val COUNTRY_FIELD = "Site"

fun getCountry(preference: SharedPreferences): String {
    return preference.getString(COUNTRY_FIELD, "")?:""
}

fun saveCountry(country: String, preference: SharedPreferences) {
    val editor = preference.edit()
    editor.putString(COUNTRY_FIELD, country)
    editor.apply()
}