package com.hhs.campus.bean

data class Student(var id:Int=0,var name:String="",var sex:String=""
                   ,var idCard:String="",var address:String="",var avatar:String ="",var phone:String="",var departments:String=""){
    fun  isNull()=(id==0 || name==""|| sex==""|| idCard==""|| address==""|| avatar=="")
}
data class Login(var id:String="",var password:String="")
data class ImageHeader(var id: Int=0,var fileName:String="")