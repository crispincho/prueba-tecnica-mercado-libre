package com.challenge.mercadolibre.features.search

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.challenge.mercadolibre.databinding.SearchFragmentBinding
import com.challenge.mercadolibre.features.MainActivity
import com.challenge.mercadolibre.features.search.viewmodel.SearchViewModel

class SearchFragment : Fragment() {

    private lateinit var viewModel: SearchViewModel

    private lateinit var binding: SearchFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SearchFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).hideActionbar()
    }

    override fun onStop() {
        super.onStop()
        (activity as MainActivity).showActionBar()
    }
}