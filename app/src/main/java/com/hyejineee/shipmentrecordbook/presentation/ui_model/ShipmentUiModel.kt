package com.hyejineee.shipmentrecordbook.presentation.ui_model

import com.hyejineee.shipmentrecordbook.data.Shipment
import com.hyejineee.shipmentrecordbook.toDate
import java.util.*

data class ShipmentUiModel(
    val id: Int? = null,
    val code: String = "",
    val receiver: String = "",
    val date: String = "",
    val price: String = "",
    val isSettled: Boolean = false,
    val isHeader: Boolean = false
) {
    fun toEntity(): Shipment {
        return Shipment(
            id,
            code,
            receiver,
            date.toDate() ?: Date(System.currentTimeMillis()),
            if (isHeader.not()) price.dropLast(3).toInt() else 0,
            isSettled
        )
    }
}
