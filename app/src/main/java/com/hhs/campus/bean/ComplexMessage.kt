package com.hhs.campus.bean

data class ComplexMessage(val id:Int=0,val avatar:String="",val name:String="",val time:String="",val hanType:String=""){
    fun getDescribe()=when (hanType){
        "comment"->"评论了你的动态"
        "great"-> "点赞了你的动态"
        else -> ""
    }
}
