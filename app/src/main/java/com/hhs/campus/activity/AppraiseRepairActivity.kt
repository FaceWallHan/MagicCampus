package com.hhs.campus.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hhs.campus.AppClient
import com.hhs.campus.R
import com.hhs.campus.bean.RepairAppraise
import com.hhs.campus.utils.showToast
import com.hhs.campus.viewModel.RepairViewModel
import com.hhs.campus.viewModel.StudentViewModel
import kotlinx.android.synthetic.main.activity_appraise.*

class AppraiseRepairActivity : AppCompatActivity() {
    private val studentViewModel by lazy { ViewModelProvider(this).get(StudentViewModel::class.java) }
    private val repairViewModel by lazy {  ViewModelProvider(this).get(RepairViewModel::class.java)}
    private var appraise=RepairAppraise()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appraise)
        setSupportActionBar(appraise_repair)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        appraise.repairId=intent.getStringExtra(AppClient.repairId) as String
        studentViewModel.refreshSelfInquire()
        studentViewModel.studentLocalLiveData.observe(this, Observer { result->
            if (result.isSuccess){
                result.getOrNull()?.let {
                    appraise.id=it.id
                    appraise.name=it.name
                }
            }
        })
        repair_rating.setOnRatingBarChangeListener { _, fl, _ ->
            var feedback=""
            when(fl){
                0f->feedback="极其不满意"
                1.0f->feedback="不满意"
                2.0f->feedback="一般"
                3.0f->feedback="满意"
                4.0f->feedback="较满意"
                5.0f->feedback="非常满意"
            }
            repair_rating_show.text=feedback
        }
        submit_repair_appraise.setOnClickListener {
            appraise.appraise= repair_rating.rating.toInt().toString()
            val description=input_repair_description.text.toString().trim()
            if (description==""){
                "请填写报修评价".showToast()
            }else{
                appraise.description=description
                repairViewModel.submitAppraise(appraise)
            }
        }
        repairViewModel.repairAppraise.observe(this, Observer { result->
            if (result.isSuccess){
                "评价成功".showToast()
                finish()
            }else{
                "评价失败".showToast()
            }
        })

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }
}