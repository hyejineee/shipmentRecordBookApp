package com.hyejineee.shipmentrecordbook.presentation.ui_model

data class SettlementUiModel(
    val id: Int?,
    val title: String,
    val startDate: String,
    val endDate: String,
    val price: String,
    val shipments: List<ShipmentUiModel>
)
