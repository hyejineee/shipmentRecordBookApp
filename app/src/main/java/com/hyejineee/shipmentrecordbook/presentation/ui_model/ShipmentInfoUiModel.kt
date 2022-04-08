package com.hyejineee.shipmentrecordbook.presentation.ui_model

import java.util.*

data class ShipmentInfoUiModel(
    val id: Long?,
    val code: String,
    val receiver: String,
    val date: String,
    val price: String,
    val isCalculate: Boolean,
)
