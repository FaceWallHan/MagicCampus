package com.hhs.campus.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.hhs.campus.Repository
import com.hhs.campus.bean.Dynamic
import okhttp3.MultipartBody

class DynamicViewModel:ViewModel() {
    //上传多张图片
    private val multipleImgLiveData=MutableLiveData<List<MultipartBody.Part>>()
    val multipleImgResponse=Transformations.switchMap(multipleImgLiveData){result->
        Repository.uploadMultipleImg(result)
    }
    fun sendMultipleImg(list:List<MultipartBody.Part>){
        multipleImgLiveData.value=list
    }
    //发表动态(文字+图片)
    private val dynamicLiveData=MutableLiveData<Dynamic>()
    val dynamicResponse=Transformations.switchMap(dynamicLiveData){result->
        Repository.releaseDynamic(result)
    }
    fun uploadDynamic(dynamic: Dynamic){
        dynamicLiveData.value=dynamic
    }
}