package com.hhs.campus.net.work

import com.hhs.campus.bean.Announcement
import com.hhs.campus.bean.Student
import com.hhs.campus.utils.MagicResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AnnouncementService {
    @POST("readAnnouncementStu")
    fun getSomeAnnouncement(@Body student: Student):Call<MagicResponse<List<Announcement>>>
}