package com.hhs.campus.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hhs.campus.AppClient
import com.hhs.campus.R
import com.hhs.campus.adapter.RepairAdapter
import com.hhs.campus.bean.Repair
import com.hhs.campus.viewModel.RepairViewModel
import com.hhs.campus.viewModel.StudentViewModel
import kotlinx.android.synthetic.main.activity_show_repair.*

class ShowRepairActivity : AppCompatActivity() {
    private val repairViewModel by lazy { ViewModelProvider(this).get(RepairViewModel::class.java) }
    private val studentViewModel by lazy { ViewModelProvider(this).get(StudentViewModel::class.java) }
    private var studentId:Int=0
    private val list=ArrayList<Repair>()
    private val adapter=RepairAdapter(list,this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_repair)
        setSupportActionBar(show_repair_toolbar)
        init()
        studentViewModel.refreshSelfInquire()
        studentViewModel.inquireLiveData.observe(this, Observer { result->
                result?.id?.let {
                    repairViewModel.getAllRepairList(it)
                    studentId=it
            }
        })

        repairViewModel.repairAllListLiveData.observe(this, Observer {
                    list.clear()
                    list.addAll(it)
                    adapter.notifyDataSetChanged()
        })
    }
    private fun init(){
        val layoutManager= LinearLayoutManager(this)
        show_repair.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        show_repair.layoutManager=layoutManager
        show_repair.adapter=adapter
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode== AppClient.appraise&&resultCode== Activity.RESULT_OK){
            repairViewModel.getAllRepairList(studentId)
            //评价成功，刷新数据
        }
    }
}