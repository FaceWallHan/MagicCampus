package com.hhs.campus.utils

enum class RepairStatus (val status:String){
    ACCEPT("受理"),
    DISPATCH("派工"),
    SIGN("签到"),
    COMPLETED("已完工"),
    EVALUATED("已评价")
}