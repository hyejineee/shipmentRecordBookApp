package com.hyejineee.shipmentrecordbook.presentation.settlement_list

import android.content.Intent
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.hyejineee.shipmentrecordbook.R
import com.hyejineee.shipmentrecordbook.base.BaseFragment
import com.hyejineee.shipmentrecordbook.databinding.FragmentSettlementsScreenBinding
import com.hyejineee.shipmentrecordbook.presentation.MainActivity
import com.hyejineee.shipmentrecordbook.presentation.add_settlement.AddSettlementActivity
import org.koin.androidx.navigation.koinNavGraphViewModel


class SettlementsScreen :
    BaseFragment<SettlementListViewModel, FragmentSettlementsScreenBinding>() {
    override val viewModel: SettlementListViewModel by koinNavGraphViewModel(R.id.nav_graph)
    override val layoutId: Int = R.layout.fragment_settlements_screen

    private val adapter = SettlementListAdapter({})

    // todo : 정산 리스트 만들기 기능
    // todo : 정산 리스트 보기
    // todo : 정산 데이터 세부 내용 보기
    override fun subscribeUiModel() {
        viewModel.settlement.observe(this) {
            adapter.submitList(it.toMutableList())
        }
    }

    override fun fetchData() {
        viewModel.fetchData()
    }

    override fun initViews() {
        (activity as MainActivity).binding.addButton.setOnClickListener {
            startActivity(Intent(context, AddSettlementActivity::class.java))
        }

        binding.settlementList.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.settlementList.adapter = adapter
    }


}