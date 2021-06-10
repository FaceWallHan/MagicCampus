package com.hhs.campus.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hhs.campus.R
import com.hhs.campus.adapter.RepairAdapter
import com.hhs.campus.viewModel.RepairViewModel
import com.hhs.campus.viewModel.StudentViewModel
import kotlinx.android.synthetic.main.activity_show_repair.*

class ShowRepairActivity : AppCompatActivity() {
    private val repairViewModel by lazy { ViewModelProvider(this).get(RepairViewModel::class.java) }
    private val studentViewModel by lazy { ViewModelProvider(this).get(StudentViewModel::class.java) }
    private var studentId =0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_repair)
        setSupportActionBar(show_repair_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        studentViewModel.refreshSelfInquire()
        studentViewModel.studentLocalLiveData.observe(this, Observer { result->
            if (result.isSuccess){
                result.getOrNull()?.id?.let {
                    studentId=it
                    repairViewModel.getAllRepairList(it)
                }
            }
        })

        repairViewModel.allRepairList.observe(this, Observer { result->
            if (result.isSuccess){
                result.getOrNull()?.let {
                    val adapter=RepairAdapter(it.data)
                    val layoutManager= LinearLayoutManager(this)
                    show_repair.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
                    show_repair.layoutManager=layoutManager
                    show_repair.adapter=adapter
                }
            }
        })
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }
    override fun onResume() {
        super.onResume()
        if (studentId!=-1){
            repairViewModel.getAllRepairList(studentId)
        }
    }
}