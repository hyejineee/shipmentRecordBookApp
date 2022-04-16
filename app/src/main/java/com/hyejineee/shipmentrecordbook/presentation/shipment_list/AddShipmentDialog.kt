package com.hyejineee.shipmentrecordbook.presentation.shipment_list

import android.content.res.Resources
import android.util.Log
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.zxing.BarcodeFormat
import com.hyejineee.shipmentrecordbook.R
import com.hyejineee.shipmentrecordbook.base.BaseBottomSheetFragment
import com.hyejineee.shipmentrecordbook.convertString
import com.hyejineee.shipmentrecordbook.data.Shipment
import com.hyejineee.shipmentrecordbook.databinding.FragmentAddShipmentScreenBinding
import com.hyejineee.shipmentrecordbook.toDate
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import org.koin.androidx.navigation.koinNavGraphViewModel
import java.util.*


class AddShipmentDialog :
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
        binding.dateTextView.setText(Date(System.currentTimeMillis()).convertString())
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

        val price = binding.priceTextView.text.toString()

        context?.let {
            if (code.isNullOrBlank()) {
                Toast.makeText(it, "상품코드를 입력해주세요.", Toast.LENGTH_SHORT).show()
                return
            }

            if (receiver.isNullOrBlank()) {
                Toast.makeText(it, "수취인을 입력해주세요.", Toast.LENGTH_SHORT).show()
                return
            }

            if (date.isNullOrBlank()) {
                Toast.makeText(it, "날짜를 입력해주세요.", Toast.LENGTH_SHORT).show()
                return
            } else {
                if (date.toDate() == null) {
                    Toast.makeText(it, "올바른 날짜 형식을 입력해주세요.", Toast.LENGTH_SHORT).show()
                    return
                }
            }

            if (price.isNullOrBlank()) {
                Toast.makeText(it, "가격을 입력해주세요.", Toast.LENGTH_SHORT).show()
                return
            }

        } ?: return

        val tempDate = date.toDate()!!
        val shipment =
            Shipment(code = code, receiver = receiver, date = tempDate, price = price.toInt())

        viewModel.saveShipment(shipment)
    }


}