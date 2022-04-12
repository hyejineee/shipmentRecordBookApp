package com.hyejineee.shipmentrecordbook.presentation.shipment_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.zxing.BarcodeFormat
import com.hyejineee.shipmentrecordbook.databinding.ItemShipmentBinding
import com.hyejineee.shipmentrecordbook.presentation.ui_model.ShipmentInfoUiModel
import com.journeyapps.barcodescanner.BarcodeEncoder

class ShipmentListAdapter : ListAdapter<ShipmentInfoUiModel, ShipmentListAdapter.ViewHolder>(
    diffUtil
) {

    inner class ViewHolder(private val binding: ItemShipmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val barcodeEncoder = BarcodeEncoder()

        fun bind(data: ShipmentInfoUiModel) {
            binding.shipmentInfo = data
            val bitmap = barcodeEncoder.encodeBitmap(data.code, BarcodeFormat.CODE_128, 150, 80)
            Glide.with(binding.root)
                .load(bitmap)
                .fitCenter()
                .into(binding.barcodeImageView)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemShipmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ShipmentInfoUiModel>() {
            override fun areItemsTheSame(
                oldItem: ShipmentInfoUiModel,
                newItem: ShipmentInfoUiModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ShipmentInfoUiModel,
                newItem: ShipmentInfoUiModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}