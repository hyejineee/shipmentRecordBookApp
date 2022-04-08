package com.hyejineee.shipmentrecordbook.base

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hyejineee.shipmentrecordbook.R
import timber.log.Timber

abstract class BaseBottomSheetFragment<VM : ViewModel, B : ViewBinding> :
    BottomSheetDialogFragment() {

    protected abstract val viewModel: VM
    protected lateinit var binding: B
    protected abstract val layoutId: Int

    protected abstract fun initViews()
    protected abstract fun subscribeUiModel()
    protected abstract fun fetchData()
    protected abstract fun setHeight()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), layoutId, null, false)
        bottomSheet.setContentView(binding.root)

        BottomSheetBehavior.from(binding.root.parent as View)
            .state = BottomSheetBehavior.STATE_EXPANDED

        setHeight()

        initViews()
        fetchData()
        subscribeUiModel()

        return bottomSheet
    }


}