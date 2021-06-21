package com.challenge.mercadolibre.core.service

import com.challenge.mercadolibre.core.service.entities.Item
import com.challenge.mercadolibre.core.service.entities.Search
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitHandler {

    private const val TIME_OUT: Long = 30

    val httpHandler: ApiService = providesRestAdapter().create(ApiService::class.java)

    private fun providesRestAdapter(): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .build()
        return Retrofit.Builder()
            .baseUrl(Endpoints.URL_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    fun getSeachItems(query: String): Single<Search> {
        return httpHandler.getSeachItems(query)
    }

    fun getItem(idItem: String): Single<Item> {
        return httpHandler.getItem(idItem)
    }
}