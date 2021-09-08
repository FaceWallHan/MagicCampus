package com.hhs.campus.net.work


import com.hhs.campus.bean.ImageHeader
import com.hhs.campus.bean.ImageResponse
import com.hhs.campus.bean.Login
import com.hhs.campus.bean.Student
import com.hhs.campus.utils.DetermineResponse
import com.hhs.campus.utils.MagicResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface StudentService {
    @POST("postStudentLogin.do")
    suspend fun login(@Body login: Login):MagicResponse<Student>

    @Multipart
    @POST("updateStuHeaderServer.do")
    suspend fun uploadFile(@Part part: MultipartBody.Part ):MagicResponse<ImageResponse>

    @POST("uploadStuHeaderDatabase.do")
    suspend fun updateHeader(@Body imageHeader: ImageHeader):DetermineResponse
}