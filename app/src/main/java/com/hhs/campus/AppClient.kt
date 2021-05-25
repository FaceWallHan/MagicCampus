package com.hhs.campus

import android.app.Application
import android.content.Context

class AppClient:Application() {
    companion object{
        lateinit var context:Context
        const val sharedName="magicCampus"
        const val repairId="repairId"
        const val repair="repair"
    }
    override fun onCreate() {
        super.onCreate()
        context=applicationContext
    }
}