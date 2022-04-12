package com.hyejineee.shipmentrecordbook.presentation.statement_list

import androidx.navigation.navGraphViewModels
import com.hyejineee.shipmentrecordbook.R
import com.hyejineee.shipmentrecordbook.base.BaseFragment
import com.hyejineee.shipmentrecordbook.databinding.FragmentStatementOfShipmentBinding


class StatementOfShipmentScreen : BaseFragment<StatementsViewModel, FragmentStatementOfShipmentBinding>() {
    override val viewModel: StatementsViewModel by navGraphViewModels(R.id.nav_graph)
    override val layoutId: Int = R.layout.fragment_statement_of_shipment

    override fun subscribeUiModel() {

    }

    override fun fetchData() {

    }

    override fun initViews() {

    }


}