package com.hhs.campus.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hhs.campus.RepositoryHilt
import com.hhs.campus.bean.*
import com.hhs.campus.net.ServiceCreator
import com.hhs.campus.utils.DetermineResponse
import com.hhs.campus.utils.MagicResponse
import kotlinx.coroutines.flow.collect
import okhttp3.MultipartBody

class RepairViewModel @ViewModelInject constructor(val repositoryHilt: RepositoryHilt):ViewModel() {
    //上传报修项目
    val imageLiveData=MutableLiveData<MagicResponse<ImageResponse>>()
    fun uploadFile(part: MultipartBody.Part){
        ServiceCreator.startRequest(viewModelScope){
            repositoryHilt.uploadFile(part).collect { imageLiveData.value=it }
        }
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
    val repairProjectLiveData=MutableLiveData<List<RepairProject>>()
    fun refreshSelfProject(){
        ServiceCreator.startRequest(viewModelScope){
            repositoryHilt.getRepairProject().collect { repairProjectLiveData.value=it.data }
        }
    }
    //选择维修区域
    val repairAreaLiveData=MutableLiveData<List<RepairArea>>()
    fun refreshSelfArea(){
        ServiceCreator.startRequest(viewModelScope){
            repositoryHilt.getRepairArea().collect { repairAreaLiveData.value=it.data }
        }
    }
    //发送维修订单
    val repairFormLiveData=MutableLiveData<DetermineResponse>()
    fun sendRepairForm(repair: Repair){
        ServiceCreator.startRequest(viewModelScope){
            repositoryHilt.sendRepairForm(repair).collect { repairFormLiveData.value=it }
        }
    }
    //获取全部维修记录
    val repairAllListLiveData=MutableLiveData<List<Repair>>()
    fun getAllRepairList(id:Int){
        ServiceCreator.startRequest(viewModelScope){
            repositoryHilt.getAllRepairList(id).collect { repairAllListLiveData.value=it.data }
        }
    }
    //获取某个订单的维修记录
    val repairRecordLiveData=MutableLiveData<List<RepairRecord>>()
    fun getRecordByRepairId(repairId:String){
        ServiceCreator.startRequest(viewModelScope){
            repositoryHilt.getRecordByRepairId(repairId).collect { repairRecordLiveData.value=it.data }
        }
    }
    //发送评价描述
    val appraiseLiveData=MutableLiveData<DetermineResponse>()
    fun submitAppraise(appraise: RepairAppraise){
        ServiceCreator.startRequest(viewModelScope){
            repositoryHilt.submitAppraise(appraise).collect { appraiseLiveData.value=it }
        }
    }
    //将dialogFragment选中的报修项目值传递给activity
    val repairLiveData=MutableLiveData<Repair>()
    fun  setRepairId(repair: Repair){
        repairLiveData.value=repair
    }
}