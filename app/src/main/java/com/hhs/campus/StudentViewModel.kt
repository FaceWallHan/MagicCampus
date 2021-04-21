package com.hhs.campus

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

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
    private val existLiveData=MutableLiveData<Boolean>()
    val statusLiveData=Transformations.switchMap(existLiveData){
        Repository.isStudentSaved()
    }
    fun  refreshSelfExit(){
        existLiveData.value=existLiveData.value
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