package com.hyejineee.shipmentrecordbook.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.hyejineee.shipmentrecordbook.data.Shipment
import java.util.*

class Converters {
    @TypeConverter
    fun dateToTimestamp(date: Date) = date.time.toLong()

    @TypeConverter
    fun timestampToDate(timestamp: Long) = Date(timestamp)

    @TypeConverter
    fun shipmentListToJson(value: List<Shipment>) = Gson().toJson(value)

    @TypeConverter
    fun jsonToShipmentList(value: String) =
        Gson().fromJson(value, Array<Shipment>::class.java).toList()
}