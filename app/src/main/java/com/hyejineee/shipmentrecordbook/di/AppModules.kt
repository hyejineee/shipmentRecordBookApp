package com.hyejineee.shipmentrecordbook.di

import com.hyejineee.shipmentrecordbook.repository.ShipmentRepository
import com.hyejineee.shipmentrecordbook.repository.ShipmentRepositoryImpl
import com.hyejineee.shipmentrecordbook.presentation.shipment_list.ShipmentsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<ShipmentRepository> { ShipmentRepositoryImpl() }
    viewModel {
        ShipmentsViewModel(get())
    }
}