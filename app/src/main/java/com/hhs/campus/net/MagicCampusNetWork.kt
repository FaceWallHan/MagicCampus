package com.hhs.campus.net


import com.hhs.campus.bean.*
import com.hhs.campus.net.work.AnnouncementService
import com.hhs.campus.net.work.DynamicService
import com.hhs.campus.net.work.RepairService
import com.hhs.campus.net.work.StudentService
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object MagicCampusNetWork {
    private val studentService= ServiceCreator.create<StudentService>()
    //挂起函数
    suspend fun loginStudent( login: Login)= studentService.login(login).await()
    suspend fun uploadHeaderFile( part: MultipartBody.Part)= studentService.uploadFile(part).await()
    suspend fun updateHeader( imageHeader: ImageHeader)= studentService.updateHeader(imageHeader).await()
    private val announcementService=ServiceCreator.create<AnnouncementService>()
    suspend fun getSomeAnnouncement()= announcementService.getSomeAnnouncement().await()
    private val repairService=ServiceCreator.create(RepairService::class.java)
    suspend fun uploadFile( part: MultipartBody.Part)= repairService.uploadFile(part).await()
    suspend fun getAllRepairList( id: Int)= repairService.getAllRepairList(id).await()
    suspend fun getRecordByRepairId( repairId: String)= repairService.getRecordByRepairId(repairId).await()
    suspend fun getRepairProject()= repairService.getRepairProject().await()
    suspend fun getRepairArea()= repairService.getRepairArea().await()
    suspend fun sendRepairForm(repair: Repair)= repairService.sendRepairForm(repair).await()
    suspend fun sendRepairFormByCode(repair: Repair)= repairService.sendRepairFormByCode(repair).await()
    suspend fun submitAppraise(repairId:Int,id: Int,name:String,appraise:String,description:String)
            = repairService.submitAppraise(repairId,id,name,appraise,description).await()
    private val dynamicService=ServiceCreator.create<DynamicService>()
    suspend fun uploadMultipleImg(list:List<MultipartBody.Part>)= dynamicService.uploadMultipleImg(list).await()
    suspend fun releaseDynamic(dynamic: Dynamic)= dynamicService.releaseDynamic(dynamic).await()
    suspend fun getAllDynamic()= dynamicService.getAllDynamic().await()
    suspend fun getMyDynamic(id:Int)= dynamicService.getMyDynamic(id).await()
    suspend fun removeMyDynamic(id:Int)= dynamicService.removeMyDynamic(id).await()
    suspend fun getAllComment(id:Int)= dynamicService.getAllComment(id).await()
    suspend fun releaseComment(comment: DynamicComment)= dynamicService.releaseComment(comment).await()
    suspend fun removeComment(id: Int)= dynamicService.removeComment(id).await()
    suspend fun getSimpleGreat(id: Int,dynamicId:Int)= dynamicService.getSimpleGreat(id,dynamicId).await()
    suspend fun changeGreatStatus(id: Int,dynamicId:Int)= dynamicService.changeGreatStatus(id,dynamicId).await()
    suspend fun querySimpleOneDynamic(dynamicId:Int)= dynamicService.querySimpleOneDynamic(dynamicId).await()
    private suspend fun <T> Call<T>.await():T{
        return suspendCoroutine { continuation ->
            enqueue(object :Callback<T>{
                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                   val body=response.body()
                    if (body!=null){
                        continuation.resume(body)
                    }else{
                        continuation.resumeWithException(RuntimeException("response body is null"))
                    }
                }
            })
        }
    }
}