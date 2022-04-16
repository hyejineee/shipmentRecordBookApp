package com.hyejineee.shipmentrecordbook.presentation.add_settlement

import androidx.lifecycle.*
import com.hyejineee.shipmentrecordbook.convertString
import com.hyejineee.shipmentrecordbook.data.Settlement
import com.hyejineee.shipmentrecordbook.data.Shipment
import com.hyejineee.shipmentrecordbook.presentation.ui_model.ShipmentUiModel
import com.hyejineee.shipmentrecordbook.presentation.ui_model.ShipmentUiModelForSettlement
import com.hyejineee.shipmentrecordbook.repository.SettlementRepository
import com.hyejineee.shipmentrecordbook.repository.ShipmentRepository
import com.hyejineee.shipmentrecordbook.toDate
import com.hyejineee.shipmentrecordbook.toLiveDate
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.*

class SettlementViewModel(
    private val shipmentRepository: ShipmentRepository,
    private val settlementRepository: SettlementRepository,
) : ViewModel() {


    private val _shipments = MutableLiveData<List<ShipmentUiModelForSettlement>>()
    private val _receiver = MutableLiveData<String>("")
    private val _sort = MutableLiveData("날짜순")

    val shipments: LiveData<List<ShipmentUiModelForSettlement>> =
        combine(_shipments.asFlow(), _receiver.asFlow(), _sort.asFlow()) { list, keyword, sort ->
            val list =
                if (keyword.isNullOrBlank()) list else list.filter {
                    it.isHeader.not() && it.receiver.contains(
                        keyword
                    ) || it.code.contains(keyword)
                }
            if (sort == "날짜순") list.sortedByDescending { it.toEntity().date } else list.filter { it.isHeader.not() }
                .sortedBy { it.receiver }
        }.asLiveData()

    private val _selected = mutableListOf<Shipment>()
    private val _selectedShipments =
        MutableLiveData<List<ShipmentUiModel>>(emptyList())
    val selectedShipments = _selectedShipments.toLiveDate()

    private val _isLoading = MutableLiveData(false)
    val isLoading = _isLoading.toLiveDate()

    val totalAmount =
        selectedShipments.asFlow().map { it.sumOf { i -> i.toEntity().price } }.asLiveData()

    fun fetchData(start: String, end: String) = viewModelScope.launch {

        val startDate = start.toDate() ?: Date(System.currentTimeMillis())
        val endDate = end.toDate() ?: Date(System.currentTimeMillis())

        _isLoading.value = true

        _shipments.value = shipmentRepository.getAllShipmentBetweenDate(startDate, endDate)
            .sortedByDescending { it.date.time }
            .groupBy { it.date }
            .map {
                listOf(
                    ShipmentUiModelForSettlement(
                        date = it.key.convertString(),
                        isHeader = true
                    )
                ) + it.value.map { s -> s.toUiModelForSettlement() }
            }
            .flatten()

        _isLoading.value = false
    }

    fun searchReceiver(receiver: String) {
        _receiver.value = receiver
    }

    fun addSelected(shipment: ShipmentUiModelForSettlement) {
        _selected.add(shipment.toEntity())
    }

    fun appendAllSelected() {
        _selectedShipments.value = _selected.sortedByDescending { it.date.time }
            .groupBy { it.date }
            .map {
                listOf(
                    ShipmentUiModel(
                        date = it.key.convertString(),
                        isHeader = true
                    )
                ) + it.value.map { s -> s.toUiModel() }
            }
            .flatten()
        _selected.clear()
    }

    fun removeSelected(shipment: ShipmentUiModel) {
        _selectedShipments.value = _selectedShipments.value?.toMutableList()?.apply {
            remove(shipment)
        } ?: _selectedShipments.value
    }

    fun saveSettlement(title: String, start: String, end: String) = viewModelScope.launch {
        val list = _selectedShipments.value?.filter { it.isHeader.not() }?.map { it.toEntity() }
            ?: emptyList()

        val startDate = start.toDate() ?: Date(System.currentTimeMillis())
        val endDate = end.toDate() ?: Date(System.currentTimeMillis())
        val settlement = Settlement(
            title = title,
            startDate = startDate,
            endDate = endDate,
            shipments = list
        )
        settlementRepository.saveSettlement(settlement)
    }

    fun sortBy(s: String) {
        _sort.value = s
    }

}