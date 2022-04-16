package com.hyejineee.shipmentrecordbook.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.hyejineee.shipmentrecordbook.data.Settlement
import kotlinx.coroutines.flow.Flow

@Dao
interface SettlementDAO {
    @Query("select * from settlement")
    fun getAll(): Flow<List<Settlement>>

    @Insert
    fun insert(settlement: Settlement)
}