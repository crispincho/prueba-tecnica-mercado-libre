package com.challenge.mercadolibre.features.home.data

import com.challenge.mercadolibre.core.service.RetrofitHandler
import com.challenge.mercadolibre.core.service.entities.Site
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

object HomeRepository {

    fun getSites(callback: HomeDataSource.SitesCallback) {
        CompositeDisposable().add(
            RetrofitHandler.getSites()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Site>>() {
                    override fun onSuccess(t: List<Site>) {
                        callback.onSuccess(t)
                    }

                    override fun onError(e: Throwable) {
                        callback.onError(e.message ?: "")
                    }
                })
        )
    }

}