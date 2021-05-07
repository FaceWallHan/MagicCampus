package com.hhs.campus

import android.app.Application
import android.content.Context

class AppClient:Application() {
    companion object{
        lateinit var context:Context
        const val sharedName="magicCampus"
    }
    override fun onCreate() {
        super.onCreate()
        context=applicationContext
    }
}