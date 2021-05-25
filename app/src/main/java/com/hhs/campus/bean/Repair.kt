package com.hhs.campus.bean

import java.io.Serializable


data class ImageResponse(val fileName:String)
data class RepairProject(val smallProject:String,val bigProject:String)
data class RepairArea(val smallArea:String,val bigArea:String)

data class Repair(var s_id:String="",
                  var repairArea:String="", var repairProject: String="", var phone:String=""
                  , var date:String="", var time :String="", var content:String="",
                  var image:String="",var address:String="",val schedule:String="已报修",
                  var appraise:String="",var id:String="",var worker:String="",var deviceId:String=""):Serializable{
    fun isNull()=(address==""||repairArea==""||repairProject==""||content=="")
}
data class RepairRecord(val name:String="",val time:String="",val status:String="",val phone:String="")
data class RepairAppraise(
    var repairId:String="",
    var id: Int=0, var name:String="", var appraise:String="", var description:String="")