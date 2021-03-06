package com.hhs.campus.utils

data class MagicResponse<T>(val code:Int, val status:String, val data:T){
    fun isSuccess()=("SUCCESS"==status&&code==200)
}
data class DetermineResponse(val code:Int, val status:String){
    fun isSuccess()=("SUCCESS"==status&&code==200)
    fun isLike()=("like"==status&&code==102)
    fun isUnLike()=("unlike"==status&&code==101)
}