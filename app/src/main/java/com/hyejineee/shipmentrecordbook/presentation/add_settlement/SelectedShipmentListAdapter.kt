package com.hyejineee.shipmentrecordbook.presentation.add_settlement

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.zxing.BarcodeFormat
import com.hyejineee.shipmentrecordbook.databinding.ItemDateHeaderBinding
import com.hyejineee.shipmentrecordbook.databinding.ItemShipmentForSettlementBinding
import com.hyejineee.shipmentrecordbook.presentation.HeaderViewHolder
import com.hyejineee.shipmentrecordbook.presentation.ui_model.ShipmentUiModelForSettlement
import com.journeyapps.barcodescanner.BarcodeEncoder

class SelectedShipmentListAdapter(private val clickListener: (ShipmentUiModelForSettlement) -> Unit) :
    ListAdapter<ShipmentUiModelForSettlement, RecyclerView.ViewHolder>(
        diffUtil
    ) {

    inner class ViewHolder(private val binding: ItemShipmentForSettlementBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val barcodeEncoder = BarcodeEncoder()

        fun bind(data: ShipmentUiModelForSettlement, position: Int) {

            binding.root.setOnClickListener {
                data.isSelected = data.isSelected.not()
                clickListener(data)
                notifyItemChanged(position)
            }

            binding.shipmentInfo = data

            val bitmap = barcodeEncoder.encodeBitmap(data.code, BarcodeFormat.CODE_128, 150, 80)
            Glide.with(binding.root)
                .load(bitmap)
                .fitCenter()
                .into(binding.barcodeImageView)

        }
    }

    override fun getItemViewType(position: Int): Int =
        if (getItem(position).isHeader) HEADER_VIEW_TYPE else ITEM_VIEW_TYPE

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        if (viewType == 0)
            ViewHolder(
                ItemShipmentForSettlementBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        else
            HeaderViewHolder(
                ItemDateHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) holder.bind(getItem(position), position)
        else (holder as HeaderViewHolder).bind(getItem(position).date)
    }

    companion object {
        const val ITEM_VIEW_TYPE = 0
        const val HEADER_VIEW_TYPE = 1

        val diffUtil = object : DiffUtil.ItemCallback<ShipmentUiModelForSettlement>() {
            override fun areItemsTheSame(
                oldItem: ShipmentUiModelForSettlement,
                newItem: ShipmentUiModelForSettlement
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ShipmentUiModelForSettlement,
                newItem: ShipmentUiModelForSettlement
            ): Boolean {
                return oldItem == newItem
            }
        }
    }


}