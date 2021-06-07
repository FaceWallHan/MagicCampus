package com.hhs.campus.bean

import java.io.Serializable

data class DynamicImage(val path:String,var isCheck:Boolean=false):Serializable
data class ImageShow(val path:String,var isFirst:Boolean=false):Serializable