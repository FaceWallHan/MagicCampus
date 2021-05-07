package com.hhs.campus.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hhs.campus.R
import com.hhs.campus.adapter.AnnouncementAdapter
import com.hhs.campus.adapter.OperateAdapter
import com.hhs.campus.viewModel.AnnouncementViewModel
import com.hhs.campus.viewModel.StudentViewModel
import kotlinx.android.synthetic.main.repair_layout.*

class RepairFragment:Fragment() {
    private val announcementViewModel by lazy { ViewModelProvider(this).get(AnnouncementViewModel::class.java) }
    private val studentViewModel by lazy { ViewModelProvider(this).get(StudentViewModel::class.java) }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        addOperate()
        addAnnouncementData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.repair_layout,container,false)
    }
    private fun  addAnnouncementData(){
        studentViewModel.refreshSelfInquire()
        studentViewModel.studentLocalLiveData.observe(viewLifecycleOwner, Observer { result->
            if (result.isSuccess){
                result.getOrNull()?.let { announcementViewModel.setStudentId(it) }
            }
        })

        announcementViewModel.listLiveDate.observe(viewLifecycleOwner, Observer { result->
            if (result.isSuccess){
                val adapter= result.getOrNull()?.let { AnnouncementAdapter(it) }
                announcementList.adapter=adapter
                val layoutManager=LinearLayoutManager(activity)
                layoutManager.orientation=LinearLayoutManager.HORIZONTAL
                announcementList.addItemDecoration(DividerItemDecoration(activity,DividerItemDecoration.HORIZONTAL))
                announcementList.layoutManager=layoutManager
            }
        })

    }
    private fun  addOperate(){
        val list= mutableListOf(resources.getString(R.string.wantRepair),
            resources.getString(R.string.waitRepair),
            resources.getString(R.string.repairRecord),
            resources.getString(R.string.waitEvaluate))
        val adapter= OperateAdapter(list)
        val layoutManager=LinearLayoutManager(activity)
        operateList.addItemDecoration(DividerItemDecoration(activity,DividerItemDecoration.VERTICAL))
        operateList.layoutManager=layoutManager
        operateList.adapter=adapter

    }
}