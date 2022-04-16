package com.hyejineee.shipmentrecordbook.presentation.settlement_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyejineee.shipmentrecordbook.presentation.ui_model.SettlementUiModel
import com.hyejineee.shipmentrecordbook.repository.SettlementRepository
import com.hyejineee.shipmentrecordbook.toLiveDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class SettlementListViewModel(
    private val settlementRepository: SettlementRepository
) : ViewModel() {

    private val _settlement = MutableLiveData<List<SettlementUiModel>>()
    val settlement: LiveData<List<SettlementUiModel>> = _settlement

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading = _isLoading.toLiveDate()

    fun fetchData() = viewModelScope.launch {
        _isLoading.value = true

        settlementRepository.getAllSettlement()
            .flowOn(Dispatchers.IO)
            .collect { item ->
                _settlement.value = item.map { it.toUiModel() }
            }

        _isLoading.value = false
    }
}
