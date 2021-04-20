package com.hhs.campus

data class StudentResponse(val status:String,var student: Student)
data class Student(var id:Int=1,var name:String="",var sex:String=""
                   ,var idCard:String="",var address:String="",var avatar:String ="")
data class Login(var id:String="1",var password:String="")