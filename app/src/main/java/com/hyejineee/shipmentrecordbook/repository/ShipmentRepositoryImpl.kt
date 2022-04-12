package com.hyejineee.shipmentrecordbook.repository

import com.hyejineee.shipmentrecordbook.data.ShipmentInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class ShipmentRepositoryImpl : ShipmentRepository {

    private val tempShipmentInfo = (0..10).map {
        ShipmentInfo(
            id = it.toLong(),
            code = "MLOW$it",
            receiver = "receiver$it",
            date = Date(System.currentTimeMillis()),
            price = "299",
            isSettled = false
        )
    }.toMutableList()

    override suspend fun getAllShipment(): List<ShipmentInfo> = withContext(Dispatchers.IO) {
        tempShipmentInfo
    }

    override suspend fun createShipment(shipment: ShipmentInfo) = withContext(Dispatchers.IO) {
        tempShipmentInfo.add(shipment)
        Unit
    }
}
