package com.hyejineee.shipmentrecordbook.presentation.shipment_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.zxing.BarcodeFormat
import com.hyejineee.shipmentrecordbook.databinding.ItemDateHeaderBinding
import com.hyejineee.shipmentrecordbook.databinding.ItemShipmentBinding
import com.hyejineee.shipmentrecordbook.presentation.HeaderViewHolder
import com.hyejineee.shipmentrecordbook.presentation.ui_model.ShipmentUiModel
import com.journeyapps.barcodescanner.BarcodeEncoder

class ShipmentListAdapter(
    private val longClickListener: ((ShipmentUiModel, Int?) -> Unit)? = null,
    private val clickListener: ((ShipmentUiModel, Int) -> Unit)? = null
) : ListAdapter<ShipmentUiModel, RecyclerView.ViewHolder>(diffUtil) {


    inner class ViewHolder(private val binding: ItemShipmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val barcodeEncoder = BarcodeEncoder()

        fun bind(data: ShipmentUiModel, position: Int? = null) {

            longClickListener?.let { listener ->
                binding.root.setOnLongClickListener {
                    listener(data, null)
                    false
                }
            }

            binding.shipmentInfo = data

            if (data.isHeader) return

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
        if (viewType == 0) {
            ViewHolder(
                ItemShipmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        } else {
            HeaderViewHolder(
                ItemDateHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) holder.bind(getItem(position))
        else (holder as? HeaderViewHolder)?.bind(getItem(position).date)
    }

    companion object {
        const val ITEM_VIEW_TYPE = 0
        const val HEADER_VIEW_TYPE = 1

        val diffUtil = object : DiffUtil.ItemCallback<ShipmentUiModel>() {
            override fun areItemsTheSame(
                oldItem: ShipmentUiModel,
                newItem: ShipmentUiModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ShipmentUiModel,
                newItem: ShipmentUiModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }


}