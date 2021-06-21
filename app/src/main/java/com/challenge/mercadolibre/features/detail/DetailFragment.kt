package com.challenge.mercadolibre.features.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.viewpager.widget.ViewPager
import com.challenge.mercadolibre.core.utilities.showLoading
import com.challenge.mercadolibre.databinding.DetailFragmentBinding
import com.challenge.mercadolibre.features.detail.adapter.PictureAdapter
import com.challenge.mercadolibre.features.detail.viewmodel.DetailViewModel

class DetailFragment : Fragment() {

    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: DetailFragmentBinding
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val idProduct = args.idProduct
        binding = DetailFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        binding.viewmodel = viewModel

        viewModel.loading.observe(viewLifecycleOwner, {
            binding.container.visibility = if (it) View.INVISIBLE else View.VISIBLE
            showLoading(it, binding.lavLoading)
        })

        viewModel.pictures.observe(viewLifecycleOwner, { picture ->
            val urls = picture.map { it.secure_url }
            loadPictures(urls)
        })

        viewModel.getDetail(idProduct)
        binding.lifecycleOwner = this
        return binding.root
    }

    private fun loadPictures(urls: List<String>) {
        binding.vpPictures.adapter = PictureAdapter(requireContext(), urls)
        binding.vpPictures.currentItem = (viewModel.actualPicture.value ?: 1) - 1
        binding.vpPictures.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                viewModel.actualPicture.value = position + 1
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
    }

}