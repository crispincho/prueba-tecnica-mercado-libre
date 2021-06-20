package com.challenge.mercadolibre.features.search

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.challenge.mercadolibre.databinding.SearchFragmentBinding
import com.challenge.mercadolibre.features.MainActivity
import com.challenge.mercadolibre.features.search.viewmodel.SearchViewModel

class SearchFragment : Fragment() {

    private lateinit var viewModel: SearchViewModel
    private lateinit var binding: SearchFragmentBinding
    private lateinit var country: String
    private val args: SearchFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        country = args.country
        binding = SearchFragmentBinding.inflate(inflater, container, false)
        binding.svQuery.setQuery(args.query, false)
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        return binding.root
    }

}