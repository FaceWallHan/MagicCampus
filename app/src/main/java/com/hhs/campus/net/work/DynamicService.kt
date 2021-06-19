package com.hhs.campus.net.work

import com.hhs.campus.bean.Dynamic
import com.hhs.campus.bean.DynamicComment
import com.hhs.campus.bean.ImageResponse
import com.hhs.campus.utils.DetermineResponse
import com.hhs.campus.utils.MagicResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface DynamicService {
    //上传选择的多张图片
    @POST("uploadMultipleImg.do")
    @Multipart
    fun uploadMultipleImg(@Part list:List<MultipartBody.Part>):Call<MagicResponse<ImageResponse>>
    //发布动态
    @POST("releaseDynamic.do")
    fun releaseDynamic(@Body dynamic: Dynamic):Call<DetermineResponse>
    //获取所有
    @GET("queryEveryone.do")
    fun getAllDynamic():Call<MagicResponse<List<Dynamic>>>
    //获取我发布的
    @FormUrlEncoded
    @POST("queryDynamic.do")
    fun getMyDynamic(@Field("id") id:Int):Call<MagicResponse<List<Dynamic>>>
    //删除动态
    @FormUrlEncoded
    @POST("modifyDynamic.do")
    fun removeMyDynamic(@Field("id") id:Int):Call<DetermineResponse>
    //获取某个动态的评论
    @FormUrlEncoded
    @POST("queryComment.do")
    fun getAllComment(@Field("id") id:Int):Call<MagicResponse<List<DynamicComment>>>
    //发布评论
    @POST("addComment.do")
    fun releaseComment(@Body comment:DynamicComment):Call<DetermineResponse>
    //删除评论
    @FormUrlEncoded
    @POST("modifyComment.do")
    fun removeComment(@Field("id")id:Int):Call<DetermineResponse>
    //
    @FormUrlEncoded
    @POST("getSimpleGreat.do")
    fun getSimpleGreat(@Field("sId")id:Int,@Field("dynamicId") dynamicId:Int):Call<DetermineResponse>
    //
    @FormUrlEncoded
    @POST("changeGreatStatus.do")
    fun changeGreatStatus(@Field("sId")id:Int,@Field("dynamicId") dynamicId:Int):Call<DetermineResponse>

    @FormUrlEncoded
    @POST("querySimpleOneDynamic.do")
    fun querySimpleOneDynamic(@Field("dynamicId") dynamicId:Int):Call<MagicResponse<Dynamic>>
}