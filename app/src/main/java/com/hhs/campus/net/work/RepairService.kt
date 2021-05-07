package com.hhs.campus.net.work

import com.hhs.campus.bean.*
import com.hhs.campus.utils.MagicResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface RepairService {

    @Multipart
    @POST("repairPhoto")
    fun uploadFile(@Part part: MultipartBody.Part ):Call<MagicResponse<ImageResponse>>

    @POST("repairProject")
    fun getRepairProject():Call<MagicResponse<List<RepairProject>>>

    @POST("repairArea")
    fun getRepairArea():Call<MagicResponse<List<RepairArea>>>

    @POST("repair")
    fun sendRepairForm(@Body repair: Repair):Call<MagicResponse<Data>>
}