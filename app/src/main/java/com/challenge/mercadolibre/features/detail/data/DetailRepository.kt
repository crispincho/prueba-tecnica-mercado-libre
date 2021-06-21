package com.challenge.mercadolibre.features.detail.data

import com.challenge.mercadolibre.core.service.RetrofitHandler
import com.challenge.mercadolibre.core.service.entities.Item
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

object DetailRepository {

    private val diposable = CompositeDisposable()

    fun getItemDetail(idProduct: String, callback: DetailDataSource.ProductCallback) {
        diposable.add(
            RetrofitHandler.getItem(idProduct)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Item>() {
                    override fun onSuccess(t: Item) {
                        callback.onSuccess(t)
                    }

                    override fun onError(e: Throwable) {
                        callback.onError(e.message ?: "")
                    }
                })
        )
    }

}