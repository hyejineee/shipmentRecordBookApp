package com.hyejineee.shipmentrecordbook.presentation.settlement_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hyejineee.shipmentrecordbook.databinding.ItemSettlementBinding
import com.hyejineee.shipmentrecordbook.presentation.ui_model.SettlementUiModel

class SettlementListAdapter(clickListener: ((SettlementUiModel) -> Unit)? = null) :
    ListAdapter<SettlementUiModel, SettlementListAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val binding: ItemSettlementBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: SettlementUiModel) {
            binding.data = data
            binding.count = data.shipments.filter { it.isHeader.not() }.size.toString()
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<SettlementUiModel>() {
            override fun areItemsTheSame(
                oldItem: SettlementUiModel,
                newItem: SettlementUiModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: SettlementUiModel,
                newItem: SettlementUiModel
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemSettlementBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}
