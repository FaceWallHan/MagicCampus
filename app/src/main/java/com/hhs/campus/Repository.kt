package com.hhs.campus

import androidx.lifecycle.liveData
import com.hhs.campus.bean.ImageHeader
import com.hhs.campus.bean.Login
import com.hhs.campus.bean.Repair
import com.hhs.campus.bean.Student
import com.hhs.campus.dao.StudentDao
import com.hhs.campus.net.MagicCampusNetWork
import kotlinx.coroutines.Dispatchers
import okhttp3.MultipartBody
import kotlin.coroutines.CoroutineContext

object Repository {
    fun saveStudent(student: Student)= fire(Dispatchers.IO){
        val success= StudentDao.saveStudent(student)
        if (success){
            Result.success(student)
        }else{
            Result.failure(RuntimeException("student status is null"))
        }
    }
    fun  isStudentSaved()=fire(Dispatchers.IO){
        val success= StudentDao.isStudentSaved()
        if (success){
            Result.success(success)
        }else{
            Result.failure(RuntimeException("student status is null"))
        }
    }
    fun  getSaveStudent()=fire(Dispatchers.IO){
        val success= StudentDao.getSaveStudent()
        success.let {
            Result.success(success)
        }
    }
    fun loginStudent(login: Login)=
        fire(Dispatchers.IO) {
            val response = MagicCampusNetWork.loginStudent(login)
            if (response.isSuccess()){
                val student=response.data
                Result.success(student)
            } else {
                Result.failure(RuntimeException("response status is ${response.status}"))
            }
        }
    fun getSomeAnnouncement()=
        fire(Dispatchers.IO) {
            val response = MagicCampusNetWork.getSomeAnnouncement()
            if (response.isSuccess()){
                val announcement=response.data
                Result.success(announcement)
            } else {
                Result.failure(RuntimeException("response status is ${response.status}"))
            }
        }
    fun uploadFile(part: MultipartBody.Part)= fire(Dispatchers.IO){
        val response=MagicCampusNetWork.uploadFile(part)
        if (response.isSuccess()){
            Result.success(response.data)
        }else{
            Result.failure(RuntimeException("response status is ${response.status}"))
        }
    }
    fun getRepairProject()= fire(Dispatchers.IO){
        val response=MagicCampusNetWork.getRepairProject()
        if (response.isSuccess()){
            Result.success(response)
        }else{
            Result.failure(RuntimeException("response status is ${response.status}"))
        }
    }
    fun getRepairArea()= fire(Dispatchers.IO){
        val response=MagicCampusNetWork.getRepairArea()
        if (response.isSuccess()){
            Result.success(response)
        }else{
            Result.failure(RuntimeException("response status is ${response.status}"))
        }
    }
    fun sendRepairForm(repair: Repair)= fire(Dispatchers.IO){
        val response=MagicCampusNetWork.sendRepairForm(repair)
        if (response.isSuccess()){
            Result.success(response)
        }else{
            Result.failure(RuntimeException("response status is ${response.status}"))
        }
    }
    fun uploadHeaderFile(part: MultipartBody.Part)= fire(Dispatchers.IO){
        val response=MagicCampusNetWork.uploadHeaderFile(part)
        if (response.isSuccess()){
            Result.success(response.data)
        }else{
            Result.failure(RuntimeException("response status is ${response.status}"))
        }
    }
    fun updateHeader(imageHeader: ImageHeader)= fire(Dispatchers.IO){
        val response=MagicCampusNetWork.updateHeader(imageHeader)
        if (response.isSuccess()){
            Result.success(response)
        }else{
            Result.failure(RuntimeException("response status is ${response.status}"))
        }
    }
    //suspend-->表示所有传入的Lambda表达式中的代码也是拥有挂起函数上下文的。
    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            //通知数据变化
            emit(result)
        }
}