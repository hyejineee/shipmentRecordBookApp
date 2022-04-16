package com.hyejineee.shipmentrecordbook.repository

import com.hyejineee.shipmentrecordbook.data.Settlement
import com.hyejineee.shipmentrecordbook.data.Shipment
import com.hyejineee.shipmentrecordbook.database.AppDatabase
import com.hyejineee.shipmentrecordbook.database.SettlementDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.util.*

class SettlementRepositoryImpl(
    private val db: SettlementDAO
) : SettlementRepository {

    private val tempSettlements = (1..10).map {
        Settlement(
            it,
            "정산내역$it",
            Date(System.currentTimeMillis()),
            Date(System.currentTimeMillis()),
            (0..5).map { index ->
                Shipment(
                    index,
                    "MLOW",
                    "receiver$index",
                    Date(System.currentTimeMillis()),
                    299
                )
            }
        )
    }

    override suspend fun getAllSettlement(): Flow<List<Settlement>> = db.getAll()

    override suspend fun saveSettlement(data: Settlement) = withContext(Dispatchers.IO) {
        db.insert(data)
    }


}
