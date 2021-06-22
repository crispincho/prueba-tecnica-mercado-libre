package com.challenge.mercadolibre.features.home.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.challenge.mercadolibre.core.service.entities.Site
import com.challenge.mercadolibre.features.home.data.HomeDataSource
import com.challenge.mercadolibre.features.home.data.HomeRepository
import java.util.*

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        const val TAG = "HomeViewModel"
    }

    val sites = MutableLiveData<List<Site>>()

    val loading = MutableLiveData<Boolean>()

    val noConnection = MutableLiveData<Boolean>()

    init {
        loading.value = false
        noConnection.value = false
    }

    fun getCountries() {
        noConnection.value = false
        loading.value = true
        HomeRepository.getSites(object : HomeDataSource.SitesCallback {
            override fun onSuccess(sites: List<Site>) {
                this@HomeViewModel.sites.value = sortSites(sites)
                loading.value = false
                noConnection.value = false
            }

            override fun onError(message: String) {
                Log.e(TAG, "error al obtener los paises: $message")
                loading.value = false
                noConnection.value = true
            }
        })
    }

    private fun sortSites(sites: List<Site>): List<Site> {
        val sortSites = mutableListOf<Site>()
        sortSites.addAll(sites)
        sortSites.sortWith { o1, o2 ->
            o1.name.compareTo(o2.name)
        }
        return sortSites
    }

}