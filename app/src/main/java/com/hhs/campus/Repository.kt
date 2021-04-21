package com.hhs.campus

import androidx.lifecycle.liveData
import com.hhs.campus.dao.StudentDao
import com.hhs.campus.net.MagicCampusNetWork
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

object Repository {
    fun saveStudent(student: Student)= fire(Dispatchers.IO){
        val success= StudentDao.saveStudent(student)
        if (success){
            Result.success(student)
        }else{
            Result.failure(RuntimeException("student status is null"))
        }
    }
    fun  isStudentSaved()=fire(Dispatchers.IO){
        val success= StudentDao.isStudentSaved()
        if (success){
            Result.success(success)
        }else{
            Result.failure(RuntimeException("student status is null"))
        }
    }
    fun  getSaveStudent()=fire(Dispatchers.IO){
        val success= StudentDao.getSaveStudent()
        success.let {
            Result.success(success)
        }
    }
    fun loginStudent(login: Login)=
        fire(Dispatchers.IO) {
            val loginStudent = MagicCampusNetWork.loginStudent(login)
            if (loginStudent.status == "200") {
                val student = loginStudent.student
                Result.success(student)
            } else {
                Result.failure(RuntimeException("response status is ${loginStudent.status}"))
            }
        }
    //suspend-->表示所有传入的Lambda表达式中的代码也是拥有挂起函数上下文的。
    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            //通知数据变化
            emit(result)
        }
}