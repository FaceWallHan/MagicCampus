package com.hhs.campus.net

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {
    private const val BASE_URL="http://39.101.165.25:8080/magiccampus/"
    private val retrofit=Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    fun <T> create(service:Class<T>):T= retrofit.create(service)
    inline fun <reified T>create():T=
        create(T::class.java)
}