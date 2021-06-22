package com.challenge.mercadolibre.features.list.data

import com.challenge.mercadolibre.core.service.RetrofitHandler
import com.challenge.mercadolibre.core.service.entities.Search
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

object ListProductsRepository {

    private val diposable = CompositeDisposable()

    fun getProducts(
        query: String,
        idSite: String,
        callback: ListProductsDataSource.ProductsCallback
    ) {
        diposable.add(
            RetrofitHandler.getSeachItems(query, idSite)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Search>() {
                    override fun onSuccess(t: Search) {
                        callback.onSuccess(t.results)
                    }

                    override fun onError(e: Throwable) {
                        callback.onError(e.message ?: "")
                    }
                })
        )
    }

}