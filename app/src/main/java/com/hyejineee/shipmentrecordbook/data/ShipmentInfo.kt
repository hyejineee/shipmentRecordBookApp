package com.hyejineee.shipmentrecordbook.data

import com.hyejineee.shipmentrecordbook.presentation.ui_model.ShipmentInfoUiModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class ShipmentInfo(
    val id:Long? = null,
    val code:String,
    val receiver:String,
    val date : LocalDate,
    val price : String,
    val isCalculate:Boolean = false
){
    fun mappingToUiModel(): ShipmentInfoUiModel {
        return ShipmentInfoUiModel(
            id = id,
            code = code,
            receiver = receiver,
            date = date.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd")),
            price = "${price}00원",
            isCalculate = isCalculate
        )
    }
}
