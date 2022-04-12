package com.hyejineee.shipmentrecordbook.presentation.shipment_list

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hyejineee.shipmentrecordbook.R
import com.hyejineee.shipmentrecordbook.base.BaseFragment
import com.hyejineee.shipmentrecordbook.databinding.FragmentShipmentsScreenBinding
import com.hyejineee.shipmentrecordbook.presentation.MainActivity
import org.koin.androidx.navigation.koinNavGraphViewModel

class ShipmentListScreen : BaseFragment<ShipmentsViewModel, FragmentShipmentsScreenBinding>() {

    override val viewModel: ShipmentsViewModel by koinNavGraphViewModel(R.id.nav_graph)
    override val layoutId: Int = R.layout.fragment_shipments_screen

    private val adapter = ShipmentListAdapter()

    override fun subscribeUiModel() {
        viewModel.shipmentList.observe(this) {
            adapter.submitList(it)
        }
    }

    override fun fetchData() {
        viewModel.fetchData()
    }

    override fun initViews() {
        binding.productList.layoutManager = LinearLayoutManager(this.context)
        binding.productList.adapter = adapter
        (this.activity as MainActivity).binding.addButton.setOnClickListener {
            findNavController().navigate(R.id.action_shipmentListScreen_to_addShipmentScreen)
        }
    }

}