package com.challenge.mercadolibre.features.home.data

import com.challenge.mercadolibre.core.service.entities.Site

interface HomeDataSource {

    interface SitesCallback {
        fun onSuccess(sites: List<Site>)
        fun onError(message: String)
    }

}