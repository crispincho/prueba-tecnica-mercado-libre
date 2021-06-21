package com.challenge.mercadolibre.core.service

import com.challenge.mercadolibre.core.service.entities.Item
import com.challenge.mercadolibre.core.service.entities.Search
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET(Endpoints.GET_SEARCH)
    fun getSeachItems(@Query("q") query: String): Single<Search>

    @GET(Endpoints.GET_ITEM)
    fun getItem(@Path("idItem") idItem: String): Single<Item>
}