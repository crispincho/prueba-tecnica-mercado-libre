package com.challenge.mercadolibre.features.list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.challenge.mercadolibre.databinding.ListProductFragmentBinding
import com.challenge.mercadolibre.features.list.adapter.ItemsAdapter
import com.challenge.mercadolibre.features.list.viewmodel.ListProductsViewModel

class ListProductsFragment : Fragment(), ListProductsNavigation {

    companion object {
        const val ARG_COUNTRY = "country"
        const val ARG_QUERY = "query"
    }

    private lateinit var viewModel: ListProductsViewModel
    private lateinit var binding: ListProductFragmentBinding
    private lateinit var country: String
    private val args: ListProductsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        country = args.country
        binding = ListProductFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(ListProductsViewModel::class.java)

        viewModel.productList.observe(viewLifecycleOwner, {
            binding.rvProducts.layoutManager = LinearLayoutManager(context)
            binding.rvProducts.adapter = ItemsAdapter(it, this)
        })

        viewModel.loading.observe(viewLifecycleOwner, {
            showLoading(it)
        })

        viewModel.getProducts(args.query)
        return binding.root
    }

    private fun showLoading(show: Boolean) {
        if (show) {
            binding.lavLoading.visibility = View.VISIBLE
            binding.lavLoading.playAnimation()
        } else {
            binding.lavLoading.visibility = View.GONE
            binding.lavLoading.pauseAnimation()
        }
    }

    override fun goToDetail(id: String) {
        Log.i("Prueba", "ir a detalle $id")
        // TODO: 20/06/21 falta navegar al detalle del item
    }

}