package com.hyejineee.shipmentrecordbook

import com.hyejineee.shipmentrecordbook.data.ShipmentInfo
import com.hyejineee.shipmentrecordbook.presentation.shipment_list.ShipmentsViewModel
import com.hyejineee.shipmentrecordbook.repository.ShipmentRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.given
import java.time.LocalDate


class ShipmentsViewModelTest : ViewModelTest() {

    private val shipmentRepositoryMock = Mockito.mock(ShipmentRepository::class.java)
    private val viewModel = ShipmentsViewModel(shipmentRepository = shipmentRepositoryMock)
    private val shipmentList = (0..10).map {
        ShipmentInfo(
            id = it.toLong(),
            code = "MLOW$it",
            receiver = "receiver$it",
            date = LocalDate.now(),
            price = "299",
            isCalculate = false
        )
    }


    @Test
    fun `모든 판매 내역을 가져온다`() = runBlocking {

        given(shipmentRepositoryMock.getAllShipment()).willReturn(shipmentList)

        viewModel.fetchData()

        val progressTest = viewModel.isLoading.test()
        val shipmentListTest = viewModel.shipmentList.test()


        shipmentListTest.assertValueSequence(
            listOf(
                emptyList(),
                shipmentList.map { it.mappingToUiModel() })
        )
        progressTest.assertValueSequence(listOf(false, true, false))
        Unit
    }
}