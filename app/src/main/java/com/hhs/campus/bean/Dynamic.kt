package com.hhs.campus.bean

import java.io.Serializable

data class DynamicImage(val path:String,var isCheck:Boolean=false):Serializable
data class ImageShow(val path:String,var isFirst:Boolean=false):Serializable
data class Dynamic(val id:Int=0,var content:String="",var mark:String="" ,var sId:Int=0,val  time:String=""
,val  name:String="")