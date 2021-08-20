package com.hhs.campus.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hhs.campus.R
import com.hhs.campus.adapter.AnnouncementAdapter
import com.hhs.campus.adapter.OperateAdapter
import com.hhs.campus.databinding.RepairLayoutBinding
import com.hhs.campus.viewModel.AnnouncementViewModel

class RepairFragment:Fragment() {
    private lateinit var binding :RepairLayoutBinding
    private val announcementViewModel by lazy { ViewModelProvider(this).get(AnnouncementViewModel::class.java) }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        addOperate()
        addAnnouncementData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding= DataBindingUtil.inflate(inflater,R.layout.repair_layout,container,false)
        return binding.root
    }
    private fun  addAnnouncementData(){
        announcementViewModel.setStudentId()
        announcementViewModel.listLiveDate.observe(viewLifecycleOwner, Observer { result->
            if (result.isSuccess){
                val adapter= result.getOrNull()?.let { AnnouncementAdapter(it) }
                binding.announcementList.adapter=adapter
                val layoutManager=LinearLayoutManager(activity)
                layoutManager.orientation=LinearLayoutManager.HORIZONTAL
                binding.announcementList.addItemDecoration(DividerItemDecoration(activity,DividerItemDecoration.HORIZONTAL))
                binding.announcementList.layoutManager=layoutManager
            }
        })

    }
    private fun  addOperate(){
        val list= mutableListOf(resources.getString(R.string.wantRepair),
            resources.getString(R.string.repairRecord),
            resources.getString(R.string.scanCode_Repair))
        val adapter= OperateAdapter(list)
        val layoutManager=LinearLayoutManager(activity)
        binding.operateList.addItemDecoration(DividerItemDecoration(activity,DividerItemDecoration.VERTICAL))
        binding.operateList.layoutManager=layoutManager
        binding.operateList.adapter=adapter
    }
}