package com.hhs.campus.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.hhs.campus.R
import com.hhs.campus.databinding.RepairDetailsBinding
import com.hhs.campus.viewModel.RepairViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepairDetailsFragment :Fragment(){
    private lateinit var binding:RepairDetailsBinding
    val viewModel by lazy { ViewModelProvider(requireActivity()).get(RepairViewModel::class.java) }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.lifecycleOwner=this
        binding.viewModel=viewModel
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.repair_details,container,false)
        return binding.root
    }
}