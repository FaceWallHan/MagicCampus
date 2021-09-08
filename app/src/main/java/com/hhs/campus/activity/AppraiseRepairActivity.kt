package com.hhs.campus.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.RatingBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hhs.campus.AppClient
import com.hhs.campus.R
import com.hhs.campus.bean.RepairAppraise
import com.hhs.campus.databinding.ActivityAppraiseBinding
import com.hhs.campus.utils.showToast
import com.hhs.campus.viewModel.RepairViewModel
import com.hhs.campus.viewModel.StudentViewModel

class AppraiseRepairActivity : AppCompatActivity() ,View.OnClickListener, RatingBar.OnRatingBarChangeListener {
    private val studentViewModel by lazy { ViewModelProvider(this).get(StudentViewModel::class.java) }
    private val repairViewModel by lazy {  ViewModelProvider(this).get(RepairViewModel::class.java)}
    private var appraise=RepairAppraise()
    private lateinit var binding:ActivityAppraiseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_appraise)
        setSupportActionBar(binding.appraiseRepair)
        appraise.repairId=intent.getIntExtra(AppClient.repairId,0)
        studentViewModel.refreshSelfInquire()
        studentViewModel.inquireLiveData.observe(this, Observer {
            appraise.studentId=it.id
            appraise.studentName=it.name
        })
        binding.repairRating.onRatingBarChangeListener = this
        binding.submitRepairAppraise.setOnClickListener (this)

        repairViewModel.appraiseLiveData.observe(this, Observer { result->
            if (result.isSuccess()){
                "评价成功".showToast()
                val intent= Intent()
                setResult(Activity.RESULT_OK,intent)
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

    override fun onClick(p0: View?) {
        if (p0?.id==R.id.submit_repair_appraise){
            appraise.appraise= binding.repairRating.rating.toInt().toString()
            val description=binding.inputRepairDescription.text.toString().trim()
            if (description.isEmpty()){
                "请填写报修评价".showToast()
            }else{
                appraise.description=description
                repairViewModel.submitAppraise(appraise)
            }
        }
    }

    override fun onRatingChanged(p0: RatingBar?, p1: Float, p2: Boolean) {
        binding.repairRatingShow.text=when(p1){
            0f->"极其不满意"
            1.0f->"不满意"
            2.0f->"一般"
            3.0f->"满意"
            4.0f->"较满意"
            5.0f->"非常满意"
            else -> ""
        }
    }
}