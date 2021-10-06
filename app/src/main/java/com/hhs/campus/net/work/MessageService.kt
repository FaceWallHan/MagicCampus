package com.hhs.campus.net.work

import com.hhs.campus.bean.ComplexMessage
import com.hhs.campus.utils.MagicResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface MessageService {
    @FormUrlEncoded
    @POST("queryGreatAndCommentRecording.do")
    suspend fun queryGreatAndCommentRecording(@Field("sId")id:Int): MagicResponse<List<ComplexMessage>>
}