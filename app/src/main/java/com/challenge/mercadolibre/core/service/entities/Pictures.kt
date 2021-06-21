package com.challenge.mercadolibre.core.service.entities

import com.google.gson.annotations.SerializedName

data class Pictures(
    @SerializedName("id")
    val id: String,
    @SerializedName("secure_url")
    val secure_url: String
)
