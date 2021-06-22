package com.challenge.mercadolibre.core.utilities

import android.view.View
import com.airbnb.lottie.LottieAnimationView
import com.challenge.mercadolibre.databinding.EmptyViewBinding
import com.challenge.mercadolibre.databinding.NoConnectionBinding
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

fun moneyFormat(valor: Int): String {
    val patter = "$ ###,###"
    val localeCOL = Locale("es", "CO")
    val nf = NumberFormat.getNumberInstance(localeCOL)
    val moneyFormat = nf as DecimalFormat
    moneyFormat.applyPattern(patter)
    return moneyFormat.format(valor)
}

fun showLoading(show: Boolean, lavLoading: LottieAnimationView) {
    if (show) {
        lavLoading.visibility = View.VISIBLE
        lavLoading.playAnimation()
    } else {
        lavLoading.visibility = View.GONE
        lavLoading.pauseAnimation()
    }
}

fun showNoConnection(show: Boolean, binding: NoConnectionBinding) {
    if (show) {
        binding.lavNoConnection.playAnimation()
        binding.root.visibility = View.VISIBLE
    } else {
        binding.lavNoConnection.pauseAnimation()
        binding.root.visibility = View.GONE
    }
}

fun showNotFound(show: Boolean, binding: EmptyViewBinding) {
    if (show) {
        binding.lavNotFound.playAnimation()
        binding.root.visibility = View.VISIBLE
    } else {
        binding.lavNotFound.pauseAnimation()
        binding.root.visibility = View.GONE
    }
}