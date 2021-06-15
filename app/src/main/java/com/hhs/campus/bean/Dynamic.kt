package com.hhs.campus.bean

import java.io.Serializable

data class DynamicImage(val path:String,var isCheck:Boolean=false):Serializable
data class ImageShow(val path:String,var isFirst:Boolean=false):Serializable
data class Dynamic(val id:Int=0,var content:String="",var mark:String="" ,var sId:Int=0,val  time:String=""
,val  name:String="",val avatar:String="",val imagesList:List<ImageItem>):Serializable
data class ImageItem(val id: Int,val url:String):Serializable
data class DynamicComment(val id: Int=0, val avatar: String="",var time: String="",var content: String="",val name:String="",var sId:Int=0,var dynamicId:Int=0)
/**
 * 对于某些情况下操作后需要刷新的数据（例如增，删，改，查）
 * 下策：再次获取student信息成功后即可重新获取列表
 * 中策：定义一个id的全局变量
 * 上策：定义一个T的全局变量，成功后再对List<T>进行增删改查
 * */