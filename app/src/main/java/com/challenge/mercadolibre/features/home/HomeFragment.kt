package com.challenge.mercadolibre.features.home

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import com.challenge.mercadolibre.R
import com.challenge.mercadolibre.core.service.entities.Site
import com.challenge.mercadolibre.core.utilities.PREFERENCE_NAME
import com.challenge.mercadolibre.core.utilities.getCountry
import com.challenge.mercadolibre.core.utilities.saveCountry
import com.challenge.mercadolibre.databinding.HomeFragmentBinding
import com.challenge.mercadolibre.features.home.adapter.SitesAdapter
import com.challenge.mercadolibre.features.home.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel

    private lateinit var binding: HomeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.sites.observe(viewLifecycleOwner, {
            loadSites(it)
        })
        viewModel.getCountries()
        return binding.root
    }

    private fun loadSites(sites: List<Site>) {
        // se obtiene el nombre del ultimo pais seleccionado para colocarlo como seleccionado en el spinner
        val country = getCountry(
            requireActivity().getSharedPreferences(
                PREFERENCE_NAME,
                Context.MODE_PRIVATE
            )
        )

        binding.spSites.adapter = SitesAdapter(sites)
        binding.spSites.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                //cada vez que se selecciona un pais lo guarda como preferencia
                saveCountry(
                    viewModel.sites.value!![position].id,
                    requireActivity().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
                )
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
        binding.spSites.setSelection(getCountryPosition(country, sites))
    }

    private fun getCountryPosition(country: String, sites: List<Site>): Int {
        //obtengo la posicion del pais guardado como preferencia de la lista de sitios obtenido del servicio
        for ((counter, site) in sites.withIndex()) {
            if (site.id == country) {
                return counter
            }
        }
        return 0
    }
}