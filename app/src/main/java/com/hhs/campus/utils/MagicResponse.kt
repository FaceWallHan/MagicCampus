package com.hhs.campus.utils

data class MagicResponse<T>(val code:Int, val status:String, val data:T){
    fun isSuccess()=("SUCCESS"==status&&code==200)
}