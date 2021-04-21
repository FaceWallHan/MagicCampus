package com.hhs.campus


import retrofit2.Call
import retrofit2.http.*

interface StudentService {
//    @FormUrlEncoded
    @POST("getStudentInfo")
    fun login(@Body login: Login):Call<StudentResponse>
}