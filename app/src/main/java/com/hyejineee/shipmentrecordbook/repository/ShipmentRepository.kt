package com.hyejineee.shipmentrecordbook.repository

import com.hyejineee.shipmentrecordbook.data.ShipmentInfo

interface ShipmentRepository {
    suspend fun getAllShipment(): List<ShipmentInfo>
    suspend fun createShipment(shipment: ShipmentInfo)
}
