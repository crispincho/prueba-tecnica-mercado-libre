package com.challenge.mercadolibre.features.firstsearch

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.challenge.mercadolibre.R
import com.challenge.mercadolibre.databinding.FirstSearchFragmentBinding

class FirstSearchFragment : Fragment() {

    private lateinit var viewModel: FirstSearchViewModel

    private lateinit var binding: FirstSearchFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.first_search_fragment, container, false)
        viewModel = ViewModelProvider(this).get(FirstSearchViewModel::class.java)
        binding.viewModel = viewModel
        return binding.root
    }

}