package com.hyejineee.shipmentrecordbook.data

import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hyejineee.shipmentrecordbook.convertString
import com.hyejineee.shipmentrecordbook.presentation.ui_model.SettlementUiModel
import java.util.*

@Entity
data class Settlement(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val title: String,
    val startDate: Date,
    val endDate: Date,
    val shipments: List<Shipment>
) {
    fun toUiModel(): SettlementUiModel {
        return SettlementUiModel(
            id,
            title,
            startDate.convertString(),
            endDate.convertString(),
            "${shipments.sumOf { it.price }}00원",
            shipments.map { it.toUiModel() }
        )
    }
}
