package com.hyejineee.shipmentrecordbook.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.hyejineee.shipmentrecordbook.data.Settlement
import com.hyejineee.shipmentrecordbook.data.Shipment
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface ShipmentDAO {
    @Query("select * from shipment")
    fun getAll(): Flow<List<Shipment>>

    @Query("select * from shipment where date between :start and :end")
    fun getAllBetweenDate(start: Long, end: Long): List<Shipment>

    @Insert
    fun insert(shipment: Shipment)
}