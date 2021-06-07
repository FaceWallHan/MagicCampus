package com.hhs.campus.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.hhs.campus.Repository
import com.hhs.campus.bean.Repair
import com.hhs.campus.bean.RepairAppraise
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
    //获取全部维修记录
    private val repairAllListLiveData=MutableLiveData<Int>()
    val allRepairList=Transformations.switchMap(repairAllListLiveData){result->
        Repository.getAllRepairList(result)
    }
    fun getAllRepairList(id:Int){
        repairAllListLiveData.value=id
    }
    //获取某个订单的维修记录
    private val repairRecordLiveData=MutableLiveData<String>()
    val repairRecord=Transformations.switchMap(repairRecordLiveData){result->
        Repository.getRecordByRepairId(result)
    }
    fun getRecordByRepairId(repairId:String){
        repairRecordLiveData.value=repairId
    }
    //发送评价描述
    private val appraiseLiveData=MutableLiveData<RepairAppraise>()
    val repairAppraise=Transformations.switchMap(appraiseLiveData){result->
        Repository.submitAppraise(result)
    }
    fun submitAppraise(appraise: RepairAppraise){
        appraiseLiveData.value=appraise
    }
    //扫码发送维修订单
    private val repairFormCodeLiveData=MutableLiveData<Repair>()
    val repairFormCode=Transformations.switchMap(repairFormCodeLiveData){result->
        Repository.sendRepairFormByCode(result)
    }
    fun sendRepairFormCode(repair: Repair){
        repairFormCodeLiveData.value=repair
    }
    //将dialogFragment选中的报修项目值传递给activity
    val repairLiveData=MutableLiveData<Repair>()
    fun  setRepairId(repair: Repair){
        repairLiveData.value=repair
    }
}