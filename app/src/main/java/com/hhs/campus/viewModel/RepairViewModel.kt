package com.hhs.campus.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.hhs.campus.Repository
import com.hhs.campus.bean.Repair
import okhttp3.MultipartBody

class RepairViewModel:ViewModel() {
    private val imageLiveData=MutableLiveData<MultipartBody.Part>()
    val imageUrl=Transformations.switchMap(imageLiveData){query->
        Repository.uploadFile(query)
    }
    fun uploadFile(part: MultipartBody.Part){
        imageLiveData.value=part
    }
    //将dialogFragment选中的报修项目值传递给activity
    val selectProjectLiveData=MutableLiveData<String>()
    fun  setSelectProjectRepair(item:String){
        selectProjectLiveData.value=item
    }
    //将dialogFragment选中的报修区域值传递给activity
    val selectAreaLiveData=MutableLiveData<String>()
    fun  setSelectAreaRepair(item:String){
        selectAreaLiveData.value=item
    }
    //选择维修项目
    private val repairProjectLiveData=MutableLiveData<Any?>()
    val projectList=Transformations.switchMap(repairProjectLiveData){
        Repository.getRepairProject()
    }
    fun refreshSelfProject(){
        repairProjectLiveData.value=repairProjectLiveData.value
    }
    //选择维修区域
    private val repairAreaLiveData=MutableLiveData<Any?>()
    val areaList=Transformations.switchMap(repairAreaLiveData){
        Repository.getRepairArea()
    }
    fun refreshSelfArea(){
        repairAreaLiveData.value=repairAreaLiveData.value
    }
    //发送维修订单
    private val repairFormLiveData=MutableLiveData<Repair>()
    val repairForm=Transformations.switchMap(repairFormLiveData){result->
        Repository.sendRepairForm(result)
    }
    fun sendRepairForm(repair: Repair){
        repairFormLiveData.value=repair
    }
}