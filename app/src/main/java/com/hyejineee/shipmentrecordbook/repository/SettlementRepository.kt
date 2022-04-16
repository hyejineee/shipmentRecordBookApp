package com.hyejineee.shipmentrecordbook.repository

import com.hyejineee.shipmentrecordbook.data.Settlement
import com.hyejineee.shipmentrecordbook.data.Shipment
import kotlinx.coroutines.flow.Flow
import java.util.*

interface SettlementRepository {
    suspend fun getAllSettlement(): Flow<List<Settlement>>
    suspend fun saveSettlement(data: Settlement)
}
