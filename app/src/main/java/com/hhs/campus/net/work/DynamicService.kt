package com.hhs.campus.net.work

import com.hhs.campus.bean.Dynamic
import com.hhs.campus.bean.DynamicComment
import com.hhs.campus.bean.ImageResponse
import com.hhs.campus.utils.DetermineResponse
import com.hhs.campus.utils.MagicResponse
import okhttp3.MultipartBody
import retrofit2.http.*

interface DynamicService {
    //上传选择的多张图片
    @POST("uploadMultipleImg.do")
    @Multipart
    suspend fun uploadMultipleImg(@Part list:List<MultipartBody.Part>):MagicResponse<ImageResponse>
    //发布动态
    @POST("releaseDynamic.do")
    suspend fun releaseDynamic(@Body dynamic: Dynamic):DetermineResponse
    //获取所有
    @GET("queryEveryone.do")
    suspend fun getAllDynamic():MagicResponse<List<Dynamic>>
    //获取我发布的
    @FormUrlEncoded
    @POST("queryDynamic.do")
    suspend fun getMyDynamic(@Field("id") id:Int):MagicResponse<List<Dynamic>>
    //删除动态
    @FormUrlEncoded
    @POST("modifyDynamic.do")
    suspend fun removeMyDynamic(@Field("id") id:Int):DetermineResponse
    //获取某个动态的评论
    @FormUrlEncoded
    @POST("queryComment.do")
    suspend fun getAllComment(@Field("id") id:Int):MagicResponse<List<DynamicComment>>
    //发布评论
    @POST("addComment.do")
    suspend fun releaseComment(@Body comment:DynamicComment):DetermineResponse
    //删除评论
    @FormUrlEncoded
    @POST("modifyComment.do")
    suspend fun removeComment(@Field("id")id:Int):DetermineResponse
    //是否被点赞
    @FormUrlEncoded
    @POST("getSimpleGreat.do")
    suspend fun getSimpleGreat(@Field("sId")id:Int,@Field("dynamicId") dynamicId:Int):DetermineResponse
    //点赞or取消点赞
    @FormUrlEncoded
    @POST("changeGreatStatus.do")
    suspend fun changeGreatStatus(@Field("sId")id:Int,@Field("dynamicId") dynamicId:Int):DetermineResponse

    @FormUrlEncoded
    @POST("querySimpleOneDynamic.do")
    suspend fun querySimpleOneDynamic(@Field("dynamicId") dynamicId:Int):MagicResponse<Dynamic>
}