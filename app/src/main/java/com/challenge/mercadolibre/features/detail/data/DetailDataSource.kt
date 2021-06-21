package com.challenge.mercadolibre.features.detail.data

import com.challenge.mercadolibre.core.service.entities.Item

interface DetailDataSource {

    //Esta interfas quita la dependencia del repository que maneja con el RetrofitHandler sobre el viewmodel
    interface ProductCallback {
        fun onSuccess(product: Item)
        fun onError(message: String)
    }

}