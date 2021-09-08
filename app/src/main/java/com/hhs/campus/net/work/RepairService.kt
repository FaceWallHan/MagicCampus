package com.hhs.campus.net.work

import com.hhs.campus.bean.*
import com.hhs.campus.utils.DetermineResponse
import com.hhs.campus.utils.MagicResponse
import okhttp3.MultipartBody
import retrofit2.http.*

interface RepairService {
    //上传报修图片
    @Multipart
    @POST("repairPhotoServer.do")
    suspend fun uploadFile(@Part part: MultipartBody.Part ):MagicResponse<ImageResponse>
    //获取维修项目
    @GET("repairProject.do")
    suspend fun getRepairProject():MagicResponse<List<RepairProject>>
    //获取维修区域
    @GET("repairArea.do")
    suspend fun getRepairArea():MagicResponse<List<RepairArea>>
    //上传维修订单
    @POST("repairUploadDatabase.do")
    suspend fun sendRepairForm(@Body repair: Repair):DetermineResponse

    //获取维修列表
    @FormUrlEncoded
    @POST("viewRepairStu.do")
    suspend fun  getAllRepairList(@Field("id") id:Int):MagicResponse<List<Repair>>

    //根据id获取维修记录
    @FormUrlEncoded
    @POST("getRecordByRepairId.do")
    suspend fun  getRecordByRepairId(@Field("repairId") repairId:String):MagicResponse<List<RepairRecord>>

    //最后评价
    @FormUrlEncoded
    @POST("updateAppraise.do")
    suspend fun  submitAppraise(@Field("repairId") repairId:Int,@Field("id") id:Int,
                        @Field("name") name:String, @Field("appraise") appraise:String,
                        @Field("description") description:String):DetermineResponse
}