package com.hhs.campus.dao

import android.annotation.SuppressLint
import android.content.Context
import com.google.gson.Gson
import com.hhs.campus.AppClient
import com.hhs.campus.Student

object StudentDao {
    private const val selfKey="student"
    @SuppressLint("CommitPrefEdits")
    fun saveStudent(student: Student): Boolean {
        val edit = sharedPreferences().edit()
        edit.putString(selfKey, Gson().toJson(student))
        return edit.commit()
    }
    fun  getSaveStudent(): Student {
        val studentJson= sharedPreferences()
            .getString(selfKey,"")
        return Gson().fromJson(studentJson, Student::class.java)
    }
    fun  isStudentSaved()= sharedPreferences().contains(selfKey)
    private fun  sharedPreferences()= AppClient.context.getSharedPreferences(
        AppClient.sharedName,Context.MODE_PRIVATE)
}