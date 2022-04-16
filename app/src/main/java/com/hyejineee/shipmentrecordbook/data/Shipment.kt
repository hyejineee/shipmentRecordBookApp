package com.hyejineee.shipmentrecordbook.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hyejineee.shipmentrecordbook.convertString
import com.hyejineee.shipmentrecordbook.presentation.ui_model.ShipmentUiModel
import com.hyejineee.shipmentrecordbook.presentation.ui_model.ShipmentUiModelForSettlement
import java.util.*

@Entity
data class Shipment(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val code: String,
    val receiver: String,
    val date: Date,
    val price: Int,
    val isSettled: Boolean = false
) {
    fun toUiModel(): ShipmentUiModel {
        return ShipmentUiModel(
            id = id,
            code = code,
            receiver = receiver,
            date = date.convertString(),
            price = "${price}00원",
            isSettled = isSettled,
        )
    }

    fun toUiModelForSettlement(): ShipmentUiModelForSettlement {
        return ShipmentUiModelForSettlement(
            id,
            code,
            receiver,
            date.convertString(),
            "${price}00원",
            isSettled,
            false
        )
    }

}
