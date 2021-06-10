package com.hhs.campus.net.work

import com.hhs.campus.bean.Dynamic
import com.hhs.campus.bean.ImageResponse
import com.hhs.campus.utils.DetermineResponse
import com.hhs.campus.utils.MagicResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface DynamicService {
    //上传选择的多张图片
    @POST("uploadMultipleImg.do")
    @Multipart
    fun uploadMultipleImg(@Part list:List<MultipartBody.Part>):Call<MagicResponse<ImageResponse>>

    @POST("releaseDynamic.do")
    fun releaseDynamic(@Body dynamic: Dynamic):Call<DetermineResponse>
}