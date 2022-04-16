package com.hyejineee.shipmentrecordbook.repository

import android.util.Log
import com.hyejineee.shipmentrecordbook.data.Shipment
import com.hyejineee.shipmentrecordbook.database.ShipmentDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.util.*

class ShipmentRepositoryImpl(
    private val db: ShipmentDAO
) : ShipmentRepository {

    private val tempShipmentInfo = (0..10).map {
        Shipment(
            id = it,
            code = "MLOW$it",
            receiver = "receiver$it",
            date = Date(System.currentTimeMillis()),
            price = 299,
            isSettled = false
        )
    }.toMutableList()

    override suspend fun getAllShipment(): Flow<List<Shipment>> = db.getAll()
    override suspend fun createShipment(shipment: Shipment) = withContext(Dispatchers.IO) {
        db.insert(shipment)
    }

    override suspend fun getAllShipmentBetweenDate(start: Date, end: Date): List<Shipment> =
        withContext(Dispatchers.IO) {
            val data = db.getAllBetweenDate(start.time, end.time)
            Log.d("ShipmentRepo", "start : ${start.time}, end :${end.time}")
            Log.d("ShipmentRepo", "${data}")
            data
        }
}
