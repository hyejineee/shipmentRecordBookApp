package com.hyejineee.shipmentrecordbook.presentation

import androidx.recyclerview.widget.RecyclerView
import com.hyejineee.shipmentrecordbook.databinding.ItemDateHeaderBinding

class HeaderViewHolder(private val binding: ItemDateHeaderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(data: String) {
        binding.date = data
    }
}