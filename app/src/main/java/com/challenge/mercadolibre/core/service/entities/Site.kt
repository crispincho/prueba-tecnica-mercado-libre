package com.challenge.mercadolibre.core.service.entities

import com.google.gson.annotations.SerializedName

data class Site(
    @SerializedName("id")
    val id: String,
    @SerializedName("default_currency_id")
    val defaultCurrencyId: String,
    @SerializedName("name")
    val name: String
)
