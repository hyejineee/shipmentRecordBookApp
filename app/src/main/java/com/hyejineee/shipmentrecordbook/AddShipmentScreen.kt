package com.hyejineee.shipmentrecordbook

import android.content.res.Resources
import androidx.navigation.fragment.findNavController
import com.hyejineee.shipmentrecordbook.base.BaseBottomSheetFragment
import com.hyejineee.shipmentrecordbook.data.ShipmentInfo
import com.hyejineee.shipmentrecordbook.databinding.FragmentAddShipmentScreenBinding
import com.hyejineee.shipmentrecordbook.presentation.shipment_list.ShipmentsViewModel
import org.koin.androidx.navigation.koinNavGraphViewModel
import java.time.LocalDate


class AddShipmentScreen :
    BaseBottomSheetFragment<ShipmentsViewModel, FragmentAddShipmentScreenBinding>() {
    override val viewModel: ShipmentsViewModel by koinNavGraphViewModel(R.id.nav_graph)
    override val layoutId: Int = R.layout.fragment_add_shipment_screen

    override fun initViews() {
        binding.saveButton.setOnClickListener {
            saveShipment()
            findNavController().popBackStack()
        }
        binding.closeButton.setOnClickListener { findNavController().popBackStack() }
    }

    override fun subscribeUiModel() {}

    override fun fetchData() {}

    override fun setHeight() {
        val p = binding.addShipmentScreenContainer.layoutParams
        p.height = ((Resources.getSystem().displayMetrics.heightPixels) * 0.9).toInt()
        binding.addShipmentScreenContainer.layoutParams = p
    }

    private fun saveShipment() {
        val code = binding.productTextView.text.toString()
        val receiver = binding.receiverTextView.text.toString()
        val date = binding.dateTextView.text.toString()
        //todo : data 문자열 변환하는 로직 추가하기
        val tempDate = LocalDate.now()
        val price = binding.priceTextView.text.toString()

        val shipment =
            ShipmentInfo(code = code, receiver = receiver, date = tempDate, price = price)

        viewModel.saveShipment(shipment)
    }

}