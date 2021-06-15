package com.hhs.campus

import androidx.lifecycle.liveData
import com.hhs.campus.bean.*
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
    fun sendRepairFormByCode(repair: Repair)= fire(Dispatchers.IO){
        val response=MagicCampusNetWork.sendRepairFormByCode(repair)
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
    fun getAllRepairList(id:Int)= fire(Dispatchers.IO){
        val response=MagicCampusNetWork.getAllRepairList(id)
        if (response.isSuccess()){
            Result.success(response)
        }else{
            Result.failure(RuntimeException("response status is ${response.status}"))
        }
    }
    fun getRecordByRepairId(repairId:String)= fire(Dispatchers.IO){
        val response=MagicCampusNetWork.getRecordByRepairId(repairId)
        if (response.isSuccess()){
            Result.success(response)
        }else{
            Result.failure(RuntimeException("response status is ${response.status}"))
        }
    }
    fun submitAppraise(appraise:RepairAppraise)= fire(Dispatchers.IO){
        val response=MagicCampusNetWork.submitAppraise(appraise.repairId,appraise.studentId,appraise.studentName,appraise.appraise,appraise.description)
        if (response.isSuccess()){
            Result.success(response)
        }else{
            Result.failure(RuntimeException("response status is ${response.status}"))
        }
    }
    fun uploadMultipleImg(list:List<MultipartBody.Part>)= fire(Dispatchers.IO){
        val response=MagicCampusNetWork.uploadMultipleImg(list)
        if (response.isSuccess()){
            Result.success(response)
        }else{
            Result.failure(RuntimeException("response status is ${response.status}"))
        }
    }
    fun releaseDynamic(dynamic: Dynamic)= fire(Dispatchers.IO){
        val response=MagicCampusNetWork.releaseDynamic(dynamic)
        if (response.isSuccess()){
            Result.success(response)
        }else{
            Result.failure(RuntimeException("response status is ${response.status}"))
        }
    }
    fun getAllDynamic()= fire(Dispatchers.IO){
        val response=MagicCampusNetWork.getAllDynamic()
        if (response.isSuccess()){
            Result.success(response)
        }else{
            Result.failure(RuntimeException("response status is ${response.status}"))
        }
    }
    fun getMyDynamic(id:Int)= fire(Dispatchers.IO){
        val response=MagicCampusNetWork.getMyDynamic(id)
        if (response.isSuccess()){
            Result.success(response)
        }else{
            Result.failure(RuntimeException("response status is ${response.status}"))
        }
    }
    fun removeMyDynamic(id:Int)= fire(Dispatchers.IO){
        val response=MagicCampusNetWork.removeMyDynamic(id)
        if (response.isSuccess()){
            Result.success(response)
        }else{
            Result.failure(RuntimeException("response status is ${response.status}"))
        }
    }
    fun getAllComment(id:Int)= fire(Dispatchers.IO){
        val response=MagicCampusNetWork.getAllComment(id)
        if (response.isSuccess()){
            Result.success(response)
        }else{
            Result.failure(RuntimeException("response status is ${response.status}"))
        }
    }
    fun releaseComment(comment: DynamicComment)= fire(Dispatchers.IO){
        val response=MagicCampusNetWork.releaseComment(comment)
        if (response.isSuccess()){
            Result.success(response)
        }else{
            Result.failure(RuntimeException("response status is ${response.status}"))
        }
    }
    fun removeComment(id: Int)= fire(Dispatchers.IO){
        val response=MagicCampusNetWork.removeComment(id)
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