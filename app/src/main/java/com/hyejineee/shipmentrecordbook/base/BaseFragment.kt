package com.hyejineee.shipmentrecordbook.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import timber.log.Timber

abstract class BaseFragment<VM:ViewModel, B:ViewBinding> : Fragment()  {

    protected abstract val viewModel:VM
    protected lateinit var binding:B
    protected abstract val layoutId:Int

    protected abstract fun initViews()
    protected abstract fun subscribeUiModel()
    protected abstract fun fetchData()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)

        initViews()
        fetchData()
        subscribeUiModel()

        return binding.root
    }




}