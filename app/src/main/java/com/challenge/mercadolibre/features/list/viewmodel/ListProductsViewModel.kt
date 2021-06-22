package com.challenge.mercadolibre.features.list.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.challenge.mercadolibre.core.service.entities.Item
import com.challenge.mercadolibre.features.list.data.ListProductsDataSource
import com.challenge.mercadolibre.features.list.data.ListProductsRepository

class ListProductsViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        const val TAG = "ListProductsViewModel"
    }

    val productList = MutableLiveData<List<Item>>()

    val loading = MutableLiveData<Boolean>()

    init {
        loading.value = false
    }

    fun getProducts(query: String, idSite: String) {
        loading.value = true
        ListProductsRepository.getProducts(
            query,
            idSite,
            object : ListProductsDataSource.ProductsCallback {
                override fun onSuccess(products: List<Item>) {
                    productList.value = products
                    loading.value = false
                }

                override fun onError(message: String) {
                    Log.e(TAG, "error al obtener los items de la busqueda: $message")
                    loading.value = false
                }
            })
    }

}