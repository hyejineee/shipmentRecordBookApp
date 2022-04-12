package com.hyejineee.shipmentrecordbook.data

import com.hyejineee.shipmentrecordbook.presentation.ui_model.ShipmentInfoUiModel
import java.text.SimpleDateFormat
import java.util.*

data class ShipmentInfo(
    val id:Long? = null,
    val code:String,
    val receiver:String,
    val date : Date,
    val price : String,
    val isSettled:Boolean = false
){
    fun mappingToUiModel(): ShipmentInfoUiModel {
        val format = SimpleDateFormat("yyyy년 MM월 dd일")
        val dateStr = format.format(date)

        return ShipmentInfoUiModel(
            id = id,
            code = code,
            receiver = receiver,
            date = dateStr,
            price = "${price}00원",
            isSettled = isSettled
        )
    }
}
