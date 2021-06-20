package com.challenge.mercadolibre.features.list.data

import com.challenge.mercadolibre.core.service.entities.Item

interface ListProductsDataSource {

    //Esta interfas quita la dependencia del repository que maneja con el RetrofitHandler sobre el viewmodel
    interface ProductsCallback {
        fun onSuccess(products: List<Item>)
        fun onError(message: String)
    }

}