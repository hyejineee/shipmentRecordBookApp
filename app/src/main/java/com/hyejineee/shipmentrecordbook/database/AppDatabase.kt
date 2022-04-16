package com.hyejineee.shipmentrecordbook.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hyejineee.shipmentrecordbook.data.Settlement
import com.hyejineee.shipmentrecordbook.data.Shipment

@Database(entities = [Settlement::class, Shipment::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun shipmentDao(): ShipmentDAO
    abstract fun settlementDao(): SettlementDAO
}