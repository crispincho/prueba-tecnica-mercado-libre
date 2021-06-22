package com.challenge.mercadolibre.features.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.challenge.mercadolibre.core.service.entities.Site
import com.challenge.mercadolibre.databinding.ItemSiteBinding
import java.util.*

class SitesAdapter(private val sites: List<Site>) : BaseAdapter() {
    override fun getCount(): Int {
        return sites.size
    }

    override fun getItem(position: Int): Any {
        return sites[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding = ItemSiteBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
        binding.tvName.text = sites[position].name
        Glide.with(parent?.context!!)
            .load(getImageUrl(sites[position].name))
            .into(binding.ivFlag)
        return binding.root
    }

    fun getImageUrl(country: String): String {
        return when (country) {
            "Ecuador" -> "https://upload.wikimedia.org/wikipedia/commons/thumb/8/87/Flag_of_Ecuador_%281900%E2%80%932009%29.svg/300px-Flag_of_Ecuador_%281900%E2%80%932009%29.svg.png"
            "Perú" -> "https://upload.wikimedia.org/wikipedia/commons/thumb/a/aa/Flag_of_Peru_%281825%E2%80%931950%29.svg/270px-Flag_of_Peru_%281825%E2%80%931950%29.svg.png"
            "Costa Rica" -> "https://www.banderas-mundo.es/data/flags/w1600/cr.png"
            "El Salvador" -> "https://upload.wikimedia.org/wikipedia/commons/thumb/3/34/Flag_of_El_Salvador.svg/1200px-Flag_of_El_Salvador.svg.png"
            "Honduras" -> "https://upload.wikimedia.org/wikipedia/commons/thumb/8/82/Flag_of_Honduras.svg/2560px-Flag_of_Honduras.svg.png"
            "Dominicana" -> "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9f/Flag_of_the_Dominican_Republic.svg/1200px-Flag_of_the_Dominican_Republic.svg.png"
            "Uruguay" -> "https://upload.wikimedia.org/wikipedia/commons/thumb/f/fe/Flag_of_Uruguay.svg/1200px-Flag_of_Uruguay.svg.png"
            "Bolivia" -> "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b3/Bandera_de_Bolivia_%28Estado%29.svg/264px-Bandera_de_Bolivia_%28Estado%29.svg.png"
            "Nicaragua" -> "https://upload.wikimedia.org/wikipedia/commons/thumb/1/19/Flag_of_Nicaragua.svg/1200px-Flag_of_Nicaragua.svg.png"
            "Argentina" -> "https://upload.wikimedia.org/wikipedia/commons/thumb/1/1a/Flag_of_Argentina.svg/200px-Flag_of_Argentina.svg.png"
            "Venezuela" -> "https://upload.wikimedia.org/wikipedia/commons/thumb/0/06/Flag_of_Venezuela.svg/200px-Flag_of_Venezuela.svg.png"
            "Cuba" -> "https://turismo.org/wp-content/uploads/2010/09/Bandera-de-Cuba.png"
            "Panamá" -> "https://upload.wikimedia.org/wikipedia/commons/thumb/a/ab/Flag_of_Panama.svg/200px-Flag_of_Panama.svg.png"
            "Paraguay" -> "https://www.ngenespanol.com/wp-content/uploads/2018/08/%C2%BFSabes-por-qu%C3%A9-la-bandera-de-Paraguay-es-%C3%BAnica-en-Am%C3%A9rica.jpg"
            "Brasil" -> "https://upload.wikimedia.org/wikipedia/commons/thumb/0/05/Flag_of_Brazil.svg/300px-Flag_of_Brazil.svg.png"
            "Guatemala" -> "https://www.lifeder.com/wp-content/uploads/2018/12/640px-Flag_of_Guatemala.svg_.png"
            "Mexico" -> "https://images-na.ssl-images-amazon.com/images/I/61syhBK5m1L._AC_SX425_.jpg"
            "Chile" -> "https://lh3.googleusercontent.com/proxy/RJz6ajkH7YAixFdZzVOTE0uyr5S097HGBjdct0hkewgBU79exBY9x_NKLN-9Iyqgn-Ncb5rcO4v7OXFNr6DZ2XtFWDfTCS5RtlqfCtoJyITp69363Q"
            "Colombia" -> "https://upload.wikimedia.org/wikipedia/commons/thumb/2/21/Flag_of_Colombia.svg/200px-Flag_of_Colombia.svg.png"
            else -> "https://upload.wikimedia.org/wikipedia/commons/thumb/2/21/Flag_of_Colombia.svg/200px-Flag_of_Colombia.svg.png"
        }
    }
}