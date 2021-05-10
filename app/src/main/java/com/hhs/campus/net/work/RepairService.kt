package com.hhs.campus.net.work

import com.hhs.campus.bean.ImageResponse
import com.hhs.campus.bean.Repair
import com.hhs.campus.bean.RepairArea
import com.hhs.campus.bean.RepairProject
import com.hhs.campus.utils.MagicResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface RepairService {

    @Multipart
    @POST("repairPhotoServer")
    fun uploadFile(@Part part: MultipartBody.Part ):Call<MagicResponse<ImageResponse>>

    @GET("repairProject")
    fun getRepairProject():Call<MagicResponse<List<RepairProject>>>

    @GET("repairArea")
    fun getRepairArea():Call<MagicResponse<List<RepairArea>>>

    @POST("repairUploadDatabase")
    fun sendRepairForm(@Body repair: Repair):Call<MagicResponse<Any?>>
}