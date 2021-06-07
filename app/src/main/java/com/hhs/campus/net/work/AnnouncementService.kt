package com.hhs.campus.net.work

import com.hhs.campus.bean.Announcement
import com.hhs.campus.utils.MagicResponse
import retrofit2.Call
import retrofit2.http.GET

interface AnnouncementService {
    @GET("viewAnnouncementStu.do")
    fun getSomeAnnouncement():Call<MagicResponse<List<Announcement>>>
}