package com.hhs.campus

import android.app.Application
import android.content.Context

class AppClient:Application() {
    companion object{
        lateinit var context:Context
        const val sharedName="magicCampus"
        const val repairId="repairId"
        const val repair="repair"
        const val imageList="imageList"
        const val imageCount="imageCount"
        const val dynamicId="dynamicId"
        const val chooseImage=1
        const val appraise=2
        const val dynamic=3
        const val remove=4
    }
    override fun onCreate() {
        super.onCreate()
        context=applicationContext
    }
}