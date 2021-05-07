package com.hhs.campus.net.work


import com.hhs.campus.bean.Login
import com.hhs.campus.bean.Student
import com.hhs.campus.utils.MagicResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface StudentService {
    @POST("getStudentLogin")
    fun login(@Body login: Login):Call<MagicResponse<Student>>
}