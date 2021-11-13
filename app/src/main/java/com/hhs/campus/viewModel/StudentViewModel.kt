package com.hhs.campus.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hhs.campus.RepositoryHilt
import com.hhs.campus.bean.ImageHeader
import com.hhs.campus.bean.ImageResponse
import com.hhs.campus.bean.Login
import com.hhs.campus.bean.Student
import com.hhs.campus.net.ServiceCreator
import com.hhs.campus.utils.DetermineResponse
import com.hhs.campus.utils.MagicResponse
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class StudentViewModel @ViewModelInject constructor(val repositoryHilt: RepositoryHilt):ViewModel() {
    //登录
    val loginLiveData=MutableLiveData<MagicResponse<Student>>()
    fun login(login: Login){
        ServiceCreator.startRequest(viewModelScope){
            repositoryHilt.loginStudent(login).collect { loginLiveData.value=it }
        }
    }
    //保存个人信息
    val saveLiveData=MutableLiveData<Boolean>()
    fun  saveStudent(student: Student){
        viewModelScope.launch {
            repositoryHilt.saveStudent(student).collect { saveLiveData.value=it }
        }
    }
    //获取本地学生信息
    val inquireLiveData=MutableLiveData<Student>()
    fun  refreshSelfInquire(){
        viewModelScope.launch {
            repositoryHilt.getSaveStudent().collect { inquireLiveData.value=it }
        }
    }
    //发送头像
    val imageLiveData=MutableLiveData<MagicResponse<ImageResponse>>()
    fun uploadFile(part: MultipartBody.Part){
        ServiceCreator.startRequest(viewModelScope){
            repositoryHilt.uploadHeaderFile(part).collect { imageLiveData.value=it }
        }
    }
    //更改学生头像
    val headLiveData=MutableLiveData<DetermineResponse>()
    fun updateHead(imageHeader: ImageHeader){
        ServiceCreator.startRequest(viewModelScope){
            repositoryHilt.updateHeader(imageHeader).collect { headLiveData.value=it }
        }
    }
}