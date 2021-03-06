package com.challenge.mercadolibre.features.detail.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.challenge.mercadolibre.R
import com.challenge.mercadolibre.core.service.entities.Item
import com.challenge.mercadolibre.core.service.entities.Pictures
import com.challenge.mercadolibre.core.utilities.moneyFormat
import com.challenge.mercadolibre.features.detail.data.DetailDataSource
import com.challenge.mercadolibre.features.detail.data.DetailRepository
import java.util.*

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        const val TAG = "DetailViewModel"
    }

    val tittle = MutableLiveData<String>()
    val condition = MutableLiveData<String>()
    val soldQuantity = MutableLiveData<String>()
    val showSoldQuantity = MutableLiveData<Boolean>()
    val price = MutableLiveData<String>()
    val pictures = MutableLiveData<List<Pictures>>()
    val actualPicture = MutableLiveData<Int>()
    val totalPictures = MutableLiveData<Int>()

    val loading = MutableLiveData<Boolean>()
    val noConnection = MutableLiveData<Boolean>()

    init {
        loading.value = false
        actualPicture.value = 1
        noConnection.value = false
    }

    fun getDetail(idProduct: String) {
        noConnection.value = false
        loading.value = true
        DetailRepository.getItemDetail(idProduct, object : DetailDataSource.ProductCallback {
            override fun onSuccess(product: Item) {
                tittle.value = product.tittle
                condition.value =
                    if (!product.condition.isNullOrEmpty() && product.condition.toUpperCase(Locale.ROOT) == "NEW")
                        getApplication<Application>().baseContext.getString(R.string.neww)
                    else
                        getApplication<Application>().baseContext.getString(R.string.used)
                soldQuantity.value = "| " + product.soldQuantity.toString() + ""
                showSoldQuantity.value = product.soldQuantity > 0
                price.value = moneyFormat(product.price)
                pictures.value = product.pictures
                totalPictures.value = product.pictures.size
                loading.value = false
                noConnection.value = false
            }

            override fun onError(message: String) {
                Log.e(TAG, "Error al obtener detalle: $message")
                loading.value = false
                noConnection.value = true
            }

        })
    }
}