package com.hhs.campus

import com.hhs.campus.Login
import com.hhs.campus.StudentResponse

import retrofit2.Call
import retrofit2.http.*

interface StudentService {
//    @FormUrlEncoded
    @POST("getStudentInfo")
    fun login(@Body login: Login):Call<StudentResponse>
}