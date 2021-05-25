package com.hhs.campus.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hhs.campus.AppClient
import com.hhs.campus.R
import com.hhs.campus.adapter.RepairRecordAdapter
import com.hhs.campus.viewModel.RepairViewModel
import kotlinx.android.synthetic.main.activity_repair_record.*

class RepairRecordActivity : AppCompatActivity() {
    private val viewModel by lazy { ViewModelProvider(this).get(RepairViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repair_record)
        setSupportActionBar(show_repair_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val repairId = intent.getSerializableExtra(AppClient.repairId) as String
        if (repairId!=""){
            viewModel.getRecordByRepairId(repairId)
        }
        viewModel.repairRecord.observe(this, Observer { result->
            if (result.isSuccess){
                val data = result.getOrNull()?.data
                data.let {
                    if (it.isNullOrEmpty()){
                        record_empty.visibility=View.INVISIBLE
                    }else{
                        val adapter=RepairRecordAdapter(it)
                        val layoutManager= LinearLayoutManager(this)
                        repair_record.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
                        repair_record.layoutManager=layoutManager
                        repair_record.adapter=adapter
                    }
                }
            }
        })
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

}