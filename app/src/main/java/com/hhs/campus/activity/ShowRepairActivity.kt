package com.hhs.campus.activity

import android.app.Activity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hhs.campus.R
import com.hhs.campus.adapter.RepairAdapter
import com.hhs.campus.bean.Repair
import com.hhs.campus.viewModel.RepairViewModel
import com.hhs.campus.viewModel.StudentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_show_repair.*

@AndroidEntryPoint
class ShowRepairActivity : AppCompatActivity() {
    val repairViewModel by lazy { ViewModelProvider(this).get(RepairViewModel::class.java) }
    val studentViewModel by lazy { ViewModelProvider(this).get(StudentViewModel::class.java) }
    private var studentId:Int=0
    private val list=ArrayList<Repair>()
    private val adapter=RepairAdapter(list)
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
        adapter.requestLaunch=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode== Activity.RESULT_OK){
                repairViewModel.getAllRepairList(studentId)
                //评价成功，刷新数据
            }
        }
        show_repair.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        show_repair.layoutManager=layoutManager
        show_repair.adapter=adapter
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }
}