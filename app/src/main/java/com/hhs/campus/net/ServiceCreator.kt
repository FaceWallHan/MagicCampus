package com.hhs.campus.net

import com.hhs.campus.utils.OtherUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceCreator {
    private const val BASE_URL="http://47.100.119.125:8080/Campus/"
    private val client=OkHttpClient.Builder()
        .callTimeout(10,TimeUnit.SECONDS)
        .build()
    private val retrofit=Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
    fun <T> create(service:Class<T>):T= retrofit.create(service)
    inline fun <reified T>create():T= create(T::class.java)
    fun startRequest(scope:CoroutineScope,block: suspend()->Unit){
        scope.launch {
            if (OtherUtils.isNetworkConnected()){
                block()
            }
        }
    }
}