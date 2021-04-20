package com.hhs.campus

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.core.content.edit
import com.google.gson.Gson

object StudentDao {
    @SuppressLint("CommitPrefEdits")
    fun saveStudent(student: Student): Boolean {
        val edit = sharedPreferences().edit()
        edit.putString("student", Gson().toJson(student))
        Log.d("111111111111111", "saveStudent: $student")
        return edit.commit()
    }
    fun  getSaveStudent(): Student {
        val studentJson= sharedPreferences().getString("student","")
        return Gson().fromJson(studentJson,Student::class.java)
    }
    fun  isStudentSaved()= sharedPreferences().contains("student")
    private fun  sharedPreferences()=AppClient.context.getSharedPreferences(AppClient.sharedName,Context.MODE_PRIVATE)
}