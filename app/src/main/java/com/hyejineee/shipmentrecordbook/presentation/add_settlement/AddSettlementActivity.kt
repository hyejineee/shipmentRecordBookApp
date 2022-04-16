package com.hyejineee.shipmentrecordbook.presentation.add_settlement

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.hyejineee.shipmentrecordbook.databinding.ActivityAddSettlementBinding
import com.hyejineee.shipmentrecordbook.presentation.shipment_list.ShipmentListAdapter
import com.hyejineee.shipmentrecordbook.presentation.ui_model.ShipmentUiModel
import com.hyejineee.shipmentrecordbook.toDate
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddSettlementActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddSettlementBinding
    private val adapter = ShipmentListAdapter(
        longClickListener = { item, _ -> deleteSelectedShipment(item) }
    )
    private val viewModel: SettlementViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddSettlementBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        subscribe()
    }

    private fun initView() {
        binding.addButton.setOnClickListener {
            val start = binding.startDateTextView.text.toString()
            val end = binding.endDateTextView.text.toString()

            dateCheck(start, end)

            SelectShipmentDialog.newInstance(start, end)
                .show(supportFragmentManager, "SelectShipmentDialog")
        }

        binding.shipmentList.layoutManager = LinearLayoutManager(this)
        binding.shipmentList.adapter = adapter

        binding.saveButton.setOnClickListener {
            saveSettlement()
        }

    }

    private fun subscribe() {
        viewModel.selectedShipments.observe(this) {
            adapter.submitList(it.toMutableList())
        }

        viewModel.totalAmount.observe(this) {
            binding.totalAmountTextView.text = "${it}00원"
        }
    }

    private fun saveSettlement() {
        val start = binding.startDateTextView.text.toString()
        val end = binding.endDateTextView.text.toString()
        var title = binding.titleTextview.text.toString()

        dateCheck(start, end)

        if (title.isNullOrBlank()) {
            title = "$start ~ $end 정산내역"
        }

        viewModel.saveSettlement(title, start, end)

        finish()
    }

    private fun deleteSelectedShipment(item: ShipmentUiModel) {
        AlertDialog.Builder(this)
            .setMessage("정산 내역에서 삭제하겠습니까?")
            .setNegativeButton("예") { dialog, _ ->
                viewModel.removeSelected(item)
                dialog.dismiss()
            }
            .setPositiveButton("아니오") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun dateCheck(start: String, end: String) {

        if (start.isNullOrBlank()) {
            Toast.makeText(this, "시작일을 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        if (end.isNullOrBlank()) {
            Toast.makeText(this, "종료일을 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        if (start.toDate() == null || end.toDate() == null) {
            Toast.makeText(this, "올바른 날짜 형태를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        if (start.toDate()?.time ?: -1 > end.toDate()?.time ?: -1) {
            Toast.makeText(this, "종료일을 시작일과 같게 또는 늦은 날로 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }
    }


}