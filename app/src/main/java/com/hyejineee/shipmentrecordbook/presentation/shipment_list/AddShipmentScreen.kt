package com.hyejineee.shipmentrecordbook.presentation.shipment_list

import android.content.res.Resources
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.zxing.BarcodeFormat
import com.hyejineee.shipmentrecordbook.R
import com.hyejineee.shipmentrecordbook.base.BaseBottomSheetFragment
import com.hyejineee.shipmentrecordbook.data.ShipmentInfo
import com.hyejineee.shipmentrecordbook.databinding.FragmentAddShipmentScreenBinding
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import org.koin.androidx.navigation.koinNavGraphViewModel
import java.util.*


class AddShipmentScreen :
    BaseBottomSheetFragment<ShipmentsViewModel, FragmentAddShipmentScreenBinding>() {
    override val viewModel: ShipmentsViewModel by koinNavGraphViewModel(R.id.nav_graph)
    override val layoutId: Int = R.layout.fragment_add_shipment_screen

    private val barcodeLauncher = registerForActivityResult(ScanContract()) { result ->
        if (result.contents == null) {
            Toast.makeText(context, "취소되었습니다.", Toast.LENGTH_SHORT).show()
            return@registerForActivityResult
        }

        val barcodeEncoder = BarcodeEncoder()
        val bitmap = barcodeEncoder.encodeBitmap(result.contents, BarcodeFormat.CODE_128, 400, 200)
        Glide.with(this)
            .load(bitmap)
            .fitCenter()
            .into(binding.barcodeImageView)
        binding.productTextView.setText(result.contents)
    }

    override fun initViews() {
        binding.saveButton.setOnClickListener {
            saveShipment()
            findNavController().popBackStack()
        }
        binding.closeButton.setOnClickListener { findNavController().popBackStack() }

        barcodeLauncher.launch(ScanOptions())

        binding.barcodeImageView.setOnClickListener { barcodeLauncher.launch(ScanOptions()) }
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
        val tempDate = Date(System.currentTimeMillis())
        val price = binding.priceTextView.text.toString()

        val shipment =
            ShipmentInfo(code = code, receiver = receiver, date = tempDate, price = price)

        viewModel.saveShipment(shipment)
    }


}