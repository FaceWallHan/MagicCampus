package com.hhs.campus.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.hhs.campus.Repository
import com.hhs.campus.bean.Login
import com.hhs.campus.bean.Student

class StudentViewModel:ViewModel() {
    private val loginLiveData=MutableLiveData<Login>()
    val studentLiveData=Transformations.switchMap(loginLiveData){query->
        Repository.loginStudent(query)
    }
    fun login(id:String ,password:String){
        loginLiveData.value= Login(id, password)
    }
    //
    private val saveLiveData=MutableLiveData<Student>()
    val saveLocalLiveData=Transformations.switchMap(saveLiveData){query->
        Repository.saveStudent(query)
    }
    fun  saveStudent(student: Student){
        saveLiveData.value=student
    }
    //
    private val inquireLiveData=MutableLiveData<Student>()
    val studentLocalLiveData=Transformations.switchMap(inquireLiveData){
        Repository.getSaveStudent()
    }
    fun  refreshSelfInquire(){
        inquireLiveData.value=inquireLiveData.value
    }
}