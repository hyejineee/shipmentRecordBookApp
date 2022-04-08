package com.hyejineee.shipmentrecordbook.presentation.shipment_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyejineee.shipmentrecordbook.data.ShipmentInfo
import com.hyejineee.shipmentrecordbook.presentation.ui_model.ShipmentInfoUiModel
import com.hyejineee.shipmentrecordbook.repository.ShipmentRepository
import kotlinx.coroutines.launch

class ShipmentsViewModel(
    private val shipmentRepository: ShipmentRepository
):ViewModel() {

    private val _isLoading = MutableLiveData(false)
    val isLoading:LiveData<Boolean> = _isLoading

    private val _shipmentList = MutableLiveData<List<ShipmentInfoUiModel>>(emptyList())
    val shipmentList:LiveData<List<ShipmentInfoUiModel>> = _shipmentList


    fun fetchData() = viewModelScope.launch {
        _isLoading.value = true

        val list = shipmentRepository.getAllShipment()
        _shipmentList.value = list.map { it.mappingToUiModel() }

        _isLoading.value = false
    }

    fun saveShipment(shipment: ShipmentInfo) = viewModelScope.launch{
        _isLoading.value = true

        shipmentRepository.createShipment(shipment)

        val list = shipmentRepository.getAllShipment()
        _shipmentList.value = list.map { it.mappingToUiModel() }

        _isLoading.value = false
    }

}