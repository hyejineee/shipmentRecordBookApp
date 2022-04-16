package com.hyejineee.shipmentrecordbook.repository

import com.hyejineee.shipmentrecordbook.data.Shipment
import kotlinx.coroutines.flow.Flow
import java.util.*

interface ShipmentRepository {
    suspend fun getAllShipment(): Flow<List<Shipment>>
    suspend fun createShipment(shipment: Shipment)
    suspend fun getAllShipmentBetweenDate(start: Date, end: Date): List<Shipment>
}
