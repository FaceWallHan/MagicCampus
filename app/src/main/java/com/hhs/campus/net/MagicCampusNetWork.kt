package com.hhs.campus.net

import com.hhs.campus.Login
import com.hhs.campus.StudentService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object MagicCampusNetWork {
    private val studentService=
        ServiceCreator.create<StudentService>()
//    //挂起函数
    suspend fun loginStudent( login: Login)= studentService.login(login).await()

    private suspend fun <T> Call<T>.await():T{
        return suspendCoroutine { continuation ->
            enqueue(object :Callback<T>{
                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                   val body=response.body()
                    if (body!=null){
                        continuation.resume(body)
                    }else{
                        continuation.resumeWithException(RuntimeException("response body is null"))
                    }
                }

            })
        }
    }
}