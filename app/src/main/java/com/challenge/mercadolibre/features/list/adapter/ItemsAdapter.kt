package com.challenge.mercadolibre.features.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.challenge.mercadolibre.core.service.entities.Item
import com.challenge.mercadolibre.core.utilities.moneyFormat
import com.challenge.mercadolibre.databinding.ItemProductBinding
import com.challenge.mercadolibre.features.list.ListProductsNavigation

class ItemsAdapter(
    private val products: List<Item>,
    private val navigator: ListProductsNavigation
) :
    RecyclerView.Adapter<ItemsAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.binding.tvTittle.text = products[position].tittle
        holder.binding.tvPrice.text = moneyFormat(products[position].price)
        Glide.with(holder.binding.root.context)
            .load(products[position].thumbnail)
            .into(holder.binding.ivThumbnail)
        holder.binding.content.setOnClickListener {
            navigator.goToDetail(products[position].id)
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }


    class ItemViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root)
}