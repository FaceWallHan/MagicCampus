package com.hhs.campus

import com.hhs.campus.bean.*
import com.hhs.campus.dao.StudentDao
import com.hhs.campus.net.ServiceCreator
import com.hhs.campus.net.work.AnnouncementService
import com.hhs.campus.net.work.DynamicService
import com.hhs.campus.net.work.RepairService
import com.hhs.campus.net.work.StudentService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody

object Repository {
    private fun <T> startFlow(param:T)= flow { emit(param) }.catch {}.flowOn(Dispatchers.IO)
    private val announcementService= ServiceCreator.create<AnnouncementService>()
    private val studentService=ServiceCreator.create<StudentService>()
    private val repairService=ServiceCreator.create<RepairService>()
    private val dynamicService=ServiceCreator.create<DynamicService>()
    suspend fun  getSomeAnnouncement()= startFlow(announcementService.getSomeAnnouncement())
    suspend fun loginStudent(login: Login)= startFlow(studentService.login(login))
    fun saveStudent(student: Student)= startFlow(StudentDao.saveStudent(student))
    fun  getSaveStudent()= startFlow(StudentDao.getSaveStudent())
    suspend fun uploadFile(part: MultipartBody.Part)= startFlow(repairService.uploadFile(part))
    suspend fun getRepairProject()= startFlow(repairService.getRepairProject())
    suspend fun getRepairArea()= startFlow(repairService.getRepairArea())
    suspend fun sendRepairForm(repair: Repair)= startFlow(repairService.sendRepairForm(repair))
    suspend fun uploadHeaderFile(part: MultipartBody.Part)= startFlow(studentService.uploadFile(part))
    suspend fun updateHeader(imageHeader: ImageHeader)= startFlow(studentService.updateHeader(imageHeader))
    suspend fun getAllRepairList(id:Int)= startFlow(repairService.getAllRepairList(id))
    suspend fun getRecordByRepairId(repairId:String)= startFlow(repairService.getRecordByRepairId(repairId))
    suspend fun submitAppraise(appraise:RepairAppraise)= startFlow(repairService.submitAppraise(
        appraise.repairId,appraise.studentId,appraise.studentName,appraise.appraise,appraise.description))
    suspend fun uploadMultipleImg(list:List<MultipartBody.Part>)= startFlow(dynamicService.uploadMultipleImg(list))
    suspend fun releaseDynamic(dynamic: Dynamic)= startFlow(dynamicService.releaseDynamic(dynamic))
    suspend fun getAllDynamic()= startFlow(dynamicService.getAllDynamic())
    suspend fun getMyDynamic(id:Int)= startFlow(dynamicService.getMyDynamic(id))
    suspend fun removeMyDynamic(id:Int)= startFlow(dynamicService.removeMyDynamic(id))
    suspend fun getAllComment(id:Int)= startFlow(dynamicService.getAllComment(id))
    suspend fun releaseComment(comment: DynamicComment)= startFlow(dynamicService.releaseComment(comment))
    suspend fun removeComment(id: Int)= startFlow(dynamicService.removeComment(id))
    suspend fun getSimpleGreat(id: Int,dynamicId:Int)= startFlow(dynamicService.getSimpleGreat(id, dynamicId))
    suspend fun changeGreatStatus(id: Int,dynamicId:Int)= startFlow(dynamicService.changeGreatStatus(id, dynamicId))
    suspend fun querySimpleOneDynamic(dynamicId:Int)= startFlow(dynamicService.querySimpleOneDynamic( dynamicId))
    //suspend-->表示所有传入的Lambda表达式中的代码也是拥有挂起函数上下文的。
}