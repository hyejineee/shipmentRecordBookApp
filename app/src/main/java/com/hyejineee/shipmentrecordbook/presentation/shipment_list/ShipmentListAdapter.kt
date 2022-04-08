package com.hyejineee.shipmentrecordbook.presentation.shipment_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hyejineee.shipmentrecordbook.databinding.ItemProductLiseBinding
import com.hyejineee.shipmentrecordbook.presentation.ui_model.ShipmentInfoUiModel

class ShipmentListAdapter : ListAdapter<ShipmentInfoUiModel, ShipmentListAdapter.ViewHolder>(
    diffUtil
) {

    inner class ViewHolder(private val binding: ItemProductLiseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ShipmentInfoUiModel) {
            binding.shipmentInfo = data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemProductLiseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ShipmentInfoUiModel>() {
            override fun areItemsTheSame(oldItem: ShipmentInfoUiModel, newItem: ShipmentInfoUiModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ShipmentInfoUiModel, newItem: ShipmentInfoUiModel): Boolean {
                return oldItem == newItem
            }


        }
    }


}