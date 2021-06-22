package com.challenge.mercadolibre.features.list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.challenge.mercadolibre.core.utilities.showLoading
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
    private val args: ListProductsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ListProductFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(ListProductsViewModel::class.java)

        viewModel.productList.observe(viewLifecycleOwner, {
            binding.rvProducts.layoutManager = LinearLayoutManager(context)
            binding.rvProducts.adapter = ItemsAdapter(it, this)
        })

        viewModel.loading.observe(viewLifecycleOwner, {
            showLoading(it, binding.lavLoading)
        })

        viewModel.getProducts(args.query, args.country)
        return binding.root
    }

    override fun goToDetail(id: String) {
        val navDirection = ListProductsFragmentDirections.actionSearchFragmentToDetailFragment(id)
        findNavController().navigate(navDirection)
    }

}