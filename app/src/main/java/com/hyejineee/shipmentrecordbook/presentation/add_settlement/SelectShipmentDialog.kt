package com.hyejineee.shipmentrecordbook.presentation.add_settlement

import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.hyejineee.shipmentrecordbook.R
import com.hyejineee.shipmentrecordbook.base.BaseBottomSheetFragment
import com.hyejineee.shipmentrecordbook.databinding.DialogSelectShipmentBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SelectShipmentDialog :
    BaseBottomSheetFragment<SettlementViewModel, DialogSelectShipmentBinding>() {

    override val viewModel: SettlementViewModel by sharedViewModel()
    override val layoutId: Int = R.layout.dialog_select_shipment

    private val adpater = SelectedShipmentListAdapter { viewModel.addSelected(it) }

    override fun initViews() {
        binding.shipmentList.layoutManager = LinearLayoutManager(context)
        binding.shipmentList.adapter = adpater

        binding.addAllButton.setOnClickListener {
            viewModel.appendAllSelected()
            dismiss()
        }

        binding.closeButton.setOnClickListener { dismiss() }

        binding.searchEditText.addTextChangedListener {
            viewModel.searchReceiver(it.toString())
        }

        context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.sort,
                android.R.layout.simple_spinner_item
            ).also { a ->
                a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.sortSpinner.adapter = a
            }
        }

        binding.sortSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.sortBy(resources.getStringArray(R.array.sort)[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    override fun subscribeUiModel() {
        viewModel.shipments.observe(this) {
            adpater.submitList(it.toMutableList())
        }
    }

    override fun fetchData() {
        arguments?.getString(START_DATE_KEY)?.let { start ->
            arguments?.getString(END_DATE_KEY)?.let { end ->
                viewModel.fetchData(start, end)
            }
        }
    }

    override fun setHeight() {
        val p = binding.appendShipmentContainer.layoutParams
        p.height = ((Resources.getSystem().displayMetrics.heightPixels) * 1.0).toInt()
        binding.appendShipmentContainer.layoutParams = p
    }

    companion object {
        private const val START_DATE_KEY = "start"
        private const val END_DATE_KEY = "end"

        fun newInstance(start: String, end: String): SelectShipmentDialog {
            val args = Bundle().apply {
                putString(START_DATE_KEY, start)
                putString(END_DATE_KEY, end)
            }

            return SelectShipmentDialog().apply { arguments = args }
        }
    }
}