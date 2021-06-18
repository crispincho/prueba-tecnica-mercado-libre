package com.challenge.mercadolibre.core.service.entities

import com.google.gson.annotations.SerializedName

data class Search(
    @SerializedName("site_id")
    val country: String,
    @SerializedName("query")
    val query: String,
    @SerializedName("results")
    val results: List<Item>,
)