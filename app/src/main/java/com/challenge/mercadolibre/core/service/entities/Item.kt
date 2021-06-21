package com.challenge.mercadolibre.core.service.entities

import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val tittle: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("sold_quantity")
    val soldQuantity: Int,
    @SerializedName("condition")
    val condition: String,
    @SerializedName("pictures")
    val pictures: List<Pictures>,
)