package com.challenge.mercadolibre.core.utilities

import android.view.View
import com.airbnb.lottie.LottieAnimationView
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