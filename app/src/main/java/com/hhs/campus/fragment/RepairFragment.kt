package com.hhs.campus.fragment

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hhs.campus.R
import com.hhs.campus.activity.ScanCodeActivity
import com.hhs.campus.activity.ShowRepairActivity
import com.hhs.campus.activity.WantRepairActivity
import com.hhs.campus.adapter.AnnouncementAdapter
import com.hhs.campus.adapter.OperateAdapter
import com.hhs.campus.databinding.RepairLayoutBinding
import com.hhs.campus.viewModel.AnnouncementViewModel
import java.util.function.Consumer

class RepairFragment: BaseViewFragment<RepairLayoutBinding>(),Consumer<Int>{
    private val announcementViewModel by lazy { ViewModelProvider(this).get(AnnouncementViewModel::class.java) }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        addOperate()
        addAnnouncementData()
    }
    private fun  addAnnouncementData(){
        announcementViewModel.requestSomeAnnouncement()
        announcementViewModel.listLiveData.observe(viewLifecycleOwner, Observer { result->
            val adapter= result?.let { AnnouncementAdapter(it) }
            binding.announcementList.adapter=adapter
            val layoutManager=LinearLayoutManager(activity)
            layoutManager.orientation=LinearLayoutManager.HORIZONTAL
            binding.announcementList.addItemDecoration(DividerItemDecoration(activity,DividerItemDecoration.HORIZONTAL))
            binding.announcementList.layoutManager=layoutManager
        })
    }
    private fun  addOperate(){
        val list= mutableListOf(resources.getString(R.string.wantRepair),
            resources.getString(R.string.repairRecord),
            resources.getString(R.string.scanCode_Repair))
        val adapter= OperateAdapter(list)
        adapter.itemListener=this
        val layoutManager=LinearLayoutManager(activity)
        binding.operateList.addItemDecoration(DividerItemDecoration(activity,DividerItemDecoration.VERTICAL))
        binding.operateList.layoutManager=layoutManager
        binding.operateList.adapter=adapter
    }

    override fun accept(t: Int) {
        when(t){
            0->startActivity(Intent(context, WantRepairActivity::class.java))
            1->startActivity(Intent(context, ShowRepairActivity::class.java))
            2->startActivity(Intent(context, ScanCodeActivity::class.java))
        }
    }

    override fun getSubLayoutId() =R.layout.repair_layout
}