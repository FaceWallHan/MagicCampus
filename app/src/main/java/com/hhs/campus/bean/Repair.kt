package com.hhs.campus.bean


data class ImageResponse(val fileName:String)
data class RepairProject(val smallProject:String,val bigProject:String)
data class RepairArea(val smallArea:String,val bigArea:String)

data class Repair(var s_id:String="",
                  var repairArea:String="", var repairProject: String="", var phone:String=""
                  , var date:String="", var time :String="", var content:String="",
                  var image:String="", var status:String="",var address:String=""){
    fun isNull()=(address==""||repairArea==""||repairProject==""||content=="")
}
data class Data(val data:String)