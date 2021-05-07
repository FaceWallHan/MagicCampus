package com.hhs.campus.utils

import android.widget.Toast
import com.hhs.campus.AppClient

fun String.showToast(duration:Int=Toast.LENGTH_SHORT){
    Toast.makeText(AppClient.context,this,duration).show()
}
fun Int.showToast(duration:Int=Toast.LENGTH_SHORT){
    Toast.makeText(AppClient.context,this,duration).show()
}