package com.hyejineee.shipmentrecordbook.di

import androidx.room.Room
import com.hyejineee.shipmentrecordbook.database.AppDatabase
import com.hyejineee.shipmentrecordbook.presentation.add_settlement.SettlementViewModel
import com.hyejineee.shipmentrecordbook.presentation.settlement_list.SettlementListViewModel
import com.hyejineee.shipmentrecordbook.repository.ShipmentRepository
import com.hyejineee.shipmentrecordbook.repository.ShipmentRepositoryImpl
import com.hyejineee.shipmentrecordbook.presentation.shipment_list.ShipmentsViewModel
import com.hyejineee.shipmentrecordbook.repository.SettlementRepository
import com.hyejineee.shipmentrecordbook.repository.SettlementRepositoryImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<ShipmentRepository> { ShipmentRepositoryImpl(get<AppDatabase>().shipmentDao()) }
    single<SettlementRepository> { SettlementRepositoryImpl(get<AppDatabase>().settlementDao()) }

    single {
        Room.inMemoryDatabaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
        ).build()
    }

    viewModel { ShipmentsViewModel(get()) }
    viewModel { SettlementViewModel(get(), get()) }
    viewModel { SettlementListViewModel(get()) }
}