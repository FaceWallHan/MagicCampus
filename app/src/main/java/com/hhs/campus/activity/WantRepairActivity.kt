package com.hhs.campus.activity

import android.Manifest
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.hhs.campus.AppClient
import com.hhs.campus.R
import com.hhs.campus.bean.Repair
import com.hhs.campus.databinding.ActivityWantRepairBinding
import com.hhs.campus.dialog.SelectImageDialog
import com.hhs.campus.dialog.SelectRepairDialog
import com.hhs.campus.utils.FileUtil
import com.hhs.campus.utils.ImageUtil
import com.hhs.campus.utils.OnAddPictureListener
import com.hhs.campus.utils.showToast
import com.hhs.campus.viewModel.RepairViewModel
import com.hhs.campus.viewModel.StudentViewModel
import kotlinx.android.synthetic.main.activity_want_repair.*
import java.io.File
import java.util.*

class WantRepairActivity : AppCompatActivity(),View.OnClickListener ,OnAddPictureListener{
    private val imageDialog  by lazy {
        val dialog=SelectImageDialog()
        dialog.addPictureListener=this
        dialog}
    private val selectDialog by lazy { SelectRepairDialog() }
    private val repairViewModel by lazy { ViewModelProvider(this).get(RepairViewModel::class.java) }
    private val studentViewModel by lazy { ViewModelProvider(this).get(StudentViewModel::class.java) }
    private var repair=Repair()
    private lateinit var progressDialog:ProgressDialog
    private val permission=1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding=DataBindingUtil.setContentView<ActivityWantRepairBinding>(this,R.layout.activity_want_repair)
        setSupportActionBar(want_repair)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val serializableExtra = intent?.getSerializableExtra(AppClient.repair)
        serializableExtra?.let { repair=it as Repair }
        progressDialog=ProgressDialog(this)
        progressDialog.setTitle("loading")
        progressDialog.setCancelable(false)
        repairViewModel.imageUrl.observe(this, androidx.lifecycle.Observer { result->
            if (result.isSuccess){
                result.getOrNull()?.let { repair.image=it.fileName }
                "上传成功".showToast()
            }else{
                "上传失败".showToast()
            }
            progressDialog.dismiss()
        })
        studentViewModel.refreshSelfInquire()
        studentViewModel.studentLocalLiveData.observe(this, androidx.lifecycle.Observer { result->
            if (result.isSuccess){
                result.getOrNull()?.let {
                    repair.phone=it.phone
                    repair.s_id=it.id.toString()
                    binding.repair=repair
                }
            }
        })
        submit_repair.setOnClickListener(this)
        choose_project.setOnClickListener (this)
        choose_area.setOnClickListener (this)
        choose_reserve_date.setOnClickListener(this)
        choose_reserve_time.setOnClickListener(this)
        choose_repair_image.setOnClickListener (this)
        repairViewModel.selectProjectLiveData.observe(this, androidx.lifecycle.Observer { result->
            choose_project.setText(result)
        })
        repairViewModel.selectAreaLiveData.observe(this, androidx.lifecycle.Observer {result->
            choose_area.setText(result)
        })
    }
    private fun chooseTime(){
        val times=resources.getStringArray(R.array.time_array)
        AlertDialog.Builder(this)
            .setTitle(resources.getString(R.string.choose_reserve_time))
            .setPositiveButton("确定",null)
            .setSingleChoiceItems(times,0) { _, p1 ->
                choose_reserve_time.setText(times[p1])
            }
            .show()
    }
    private fun checkRepairInfo(){
        if (repair.content.length<2||repair.content.length>50){
            "报修内容限制为2~50字".showToast()
            return
        }
        if(repair.isNull()){
            "报修区域，报修地址，报修项目,报修内容不能为空!".showToast()
            return
        }
        if (repair.worker.isEmpty()){
            //普通报修
            repairViewModel.sendRepairForm(repair)
            repairViewModel.repairForm.observe(this, androidx.lifecycle.Observer { result->
                if (result.isSuccess){
                    "发送成功".showToast()
                }else{
                    "发送失败".showToast()
                }
                progressDialog.dismiss()
            })
        }else{
            if (!repair.isCodeNull()){
                //扫码报修
                repairViewModel.sendRepairFormCode(repair)
                repairViewModel.repairFormCode.observe(this, androidx.lifecycle.Observer { result->
                    if (result.isSuccess){
                        "发送成功".showToast()
                    }else{
                        "发送失败".showToast()
                    }
                    progressDialog.dismiss()
                })
            }
        }
        progressDialog.show()
    }
    private fun chooseDate(){
        val calendar=Calendar.getInstance()
        val dialog= DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener { _, p1, p2, p3 ->
                val date = if (p2<9){
                    "$p1-0${p2+1}-$p3"
                }else{
                    "$p1-${p2+1}-$p3"
                }
                choose_reserve_date.setText(date)
            },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH))
        dialog.show()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.choose_reserve_date->chooseDate()
            R.id.choose_reserve_time->chooseTime()
            R.id.choose_repair_image->{
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    imageDialog.show(supportFragmentManager,"")
                }else{
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), permission)
                }
            }
            R.id.choose_project->{
                selectDialog.show(supportFragmentManager,"")
                repairViewModel.refreshSelfProject()
            }
            R.id.choose_area->{
                selectDialog.show(supportFragmentManager,"")
                repairViewModel.refreshSelfArea()
            }
            R.id.submit_repair->checkRepairInfo()
        }
    }

    override fun choosePicture(data: Uri) {
        val bitmap=ImageUtil.getBitmapFromUri(data,this)
        choose_repair_image.setImageBitmap(bitmap)
        imageDialog.dismiss()
        val fileName = FileUtil.getPath(this,data)
        ImageUtil.uploadLocalImage(File(fileName),this){ part->
            repairViewModel.uploadFile(part)
            progressDialog.show()
        }
    }
    override fun takePicture(imageUri: Uri, file: File) {
        val bitmap= BitmapFactory.decodeStream(contentResolver.openInputStream(imageUri))
        choose_repair_image.setImageBitmap(ImageUtil.rotateIfRequired(bitmap,file))
        imageDialog.dismiss()
        ImageUtil.uploadLocalImage(file,this){ part->
            repairViewModel.uploadFile(part)
            progressDialog.show()
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            permission ->{
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    imageDialog.show(supportFragmentManager,"")
                } else {
                    "申请相机权限被拒绝".showToast()
                }
            }
        }
    }

}