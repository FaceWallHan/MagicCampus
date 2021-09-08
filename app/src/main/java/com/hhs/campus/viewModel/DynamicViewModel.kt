package com.hhs.campus.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hhs.campus.Repository
import com.hhs.campus.bean.Dynamic
import com.hhs.campus.bean.DynamicComment
import com.hhs.campus.bean.ImageResponse
import com.hhs.campus.net.ServiceCreator
import com.hhs.campus.utils.DetermineResponse
import com.hhs.campus.utils.MagicResponse
import kotlinx.coroutines.flow.collect
import okhttp3.MultipartBody

class DynamicViewModel:ViewModel() {
    //上传多张图片
    val multipleImgLiveData=MutableLiveData<ImageResponse>()
    fun sendMultipleImg(list:List<MultipartBody.Part>){
        ServiceCreator.startRequest(viewModelScope){
            Repository.uploadMultipleImg(list).collect { multipleImgLiveData.value=it.data }
        }
    }
    //发表动态(文字+图片)
    val dynamicLiveData=MutableLiveData<DetermineResponse>()
    fun uploadDynamic(dynamic: Dynamic){
        ServiceCreator.startRequest(viewModelScope){
            Repository.releaseDynamic(dynamic).collect { dynamicLiveData.value=it }
        }
    }
    //获取全部动态
    val allDynamicLiveData=MutableLiveData<List<Dynamic>>()
    fun refreshAllDynamic(){
    ServiceCreator.startRequest(viewModelScope){
        Repository.getAllDynamic().collect { allDynamicLiveData.value=it.data }
    }
    }
    //自己发布的动态
    val myDynamicLiveData=MutableLiveData<List<Dynamic>>()
    fun  setMyStudentId(id:Int){
        ServiceCreator.startRequest(viewModelScope){
            Repository.getMyDynamic(id).collect { myDynamicLiveData.value=it.data }
        }
    }
    //删除动态
    val removeLiveData=MutableLiveData<DetermineResponse>()
    fun  setRemoveId(id:Int){
        ServiceCreator.startRequest(viewModelScope){
            Repository.removeMyDynamic(id).collect { removeLiveData.value=it }
        }
    }
    //获取某个动态的评论
    val commentIdLiveData=MutableLiveData<MagicResponse<List<DynamicComment>>>()
    fun setDynamicId(id:Int){
        ServiceCreator.startRequest(viewModelScope){
            Repository.getAllComment(id).collect { commentIdLiveData.value=it }
        }
    }
    //发布评论
    val releaseCommentLiveData=MutableLiveData<DetermineResponse>()
    fun sendComment(dynamicComment: DynamicComment){
        ServiceCreator.startRequest(viewModelScope){
            Repository.releaseComment(dynamicComment).collect { releaseCommentLiveData.value=it }
        }
    }
    //删除评论
    val removeCommentLiveData=MutableLiveData<DetermineResponse>()
    fun removeComment(id: Int){
        ServiceCreator.startRequest(viewModelScope){
            Repository.removeComment(id).collect { removeCommentLiveData.value=it }
        }
    }
    //是否点赞
    val greatInfoLiveData=MutableLiveData<DetermineResponse>()
    fun inflateGreatInfo(dynamic: Dynamic){
        ServiceCreator.startRequest(viewModelScope){
            Repository.getSimpleGreat(dynamic.sId,dynamic.id).collect { greatInfoLiveData.value=it }
        }
    }
    //点赞/取消点赞
    val greatStatusLiveData=MutableLiveData<DetermineResponse>()
    fun inflateGreatStatusInfo(dynamic: Dynamic){
        ServiceCreator.startRequest(viewModelScope){
            Repository.changeGreatStatus(dynamic.sId,dynamic.id).collect { greatStatusLiveData.value=it }
        }
    }
    //获取单个动态根据dynamicId
    val simpleDynamicLiveData=MutableLiveData<Dynamic>()
    fun setSimpleDynamicId(dynamicId:Int){
        ServiceCreator.startRequest(viewModelScope){
            Repository.querySimpleOneDynamic(dynamicId).collect { simpleDynamicLiveData.value=it.data }
        }
    }
}