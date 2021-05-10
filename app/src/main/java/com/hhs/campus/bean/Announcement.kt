package com.hhs.campus.bean

import java.io.Serializable

data class Announcement(val author:String,val content:String,
                        val title:String,val image:String,val time:String):Serializable

