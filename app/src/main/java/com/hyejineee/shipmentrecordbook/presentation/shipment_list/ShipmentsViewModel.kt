package com.hyejineee.shipmentrecordbook.presentation.shipment_list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyejineee.shipmentrecordbook.convertString
import com.hyejineee.shipmentrecordbook.data.Shipment
import com.hyejineee.shipmentrecordbook.presentation.ui_model.ShipmentUiModel
import com.hyejineee.shipmentrecordbook.repository.ShipmentRepository
import com.hyejineee.shipmentrecordbook.toLiveDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class ShipmentsViewModel(
    private val shipmentRepository: ShipmentRepository
) : ViewModel() {

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _shipmentList = MutableLiveData<List<ShipmentUiModel>>(emptyList())
    val shipmentList = _shipmentList.toLiveDate()


    fun fetchData() = viewModelScope.launch {
        _isLoading.value = true

        shipmentRepository.getAllShipment()
            .flowOn(Dispatchers.IO)
            .collect { item ->
                _shipmentList.value = item
                    .sortedByDescending { it.date.time }
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


            }

        _isLoading.value = false
    }

    fun saveShipment(shipment: Shipment) = viewModelScope.launch {
        _isLoading.value = true

        shipmentRepository.createShipment(shipment)

        _isLoading.value = false
    }

}