package com.hhs.campus.net.work

import com.hhs.campus.bean.*
import com.hhs.campus.utils.DetermineResponse
import com.hhs.campus.utils.MagicResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface RepairService {
    //上传报修图片
    @Multipart
    @POST("repairPhotoServer.do")
    fun uploadFile(@Part part: MultipartBody.Part ):Call<MagicResponse<ImageResponse>>
    //获取维修项目
    @GET("repairProject.do")
    fun getRepairProject():Call<MagicResponse<List<RepairProject>>>
    //获取维修区域
    @GET("repairArea.do")
    fun getRepairArea():Call<MagicResponse<List<RepairArea>>>
    //上传维修订单
    @POST("repairUploadDatabase.do")
    fun sendRepairForm(@Body repair: Repair):Call<DetermineResponse>

    //根据进度获取维修列表
    @FormUrlEncoded
    @POST("viewRepairStu.do")
    fun  getAllRepairList(@Field("id") id:Int):Call<MagicResponse<List<Repair>>>

    //根据进度获取维修列表
    @FormUrlEncoded
    @POST("queryStudentScheduleList")
    fun  getStudentScheduleList(@Field("id") id:Int,@Field("name") name:String,
                                @Field("schedule") schedule:String):Call<MagicResponse<List<Repair>>>
    //根据id获取维修记录
    @FormUrlEncoded
    @POST("getRecordByRepairId.do")
    fun  getRecordByRepairId(@Field("repairId") repairId:String):Call<MagicResponse<List<RepairRecord>>>

    //最后评价
    @FormUrlEncoded
    @POST("updateAppraise.do")
    fun  submitAppraise(@Field("repairId") repairId:Int,@Field("id") id:Int,
                        @Field("name") name:String, @Field("appraise") appraise:String,
                        @Field("description") description:String):Call<DetermineResponse>
    //扫码上传维修订单
    @POST("repairUploadDatabaseByCode.do")
    fun sendRepairFormByCode(@Body repair: Repair):Call<DetermineResponse>
}