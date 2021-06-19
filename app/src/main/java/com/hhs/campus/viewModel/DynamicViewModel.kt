package com.hhs.campus.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.hhs.campus.Repository
import com.hhs.campus.bean.Dynamic
import com.hhs.campus.bean.DynamicComment
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
    //获取全部动态
    private val allDynamicLiveData=MutableLiveData<Any?>()
    val allDynamicList=Transformations.switchMap(allDynamicLiveData){
        Repository.getAllDynamic()
    }
    fun refreshAllDynamic(){
        allDynamicLiveData.value=allDynamicLiveData
    }
    //自己发布的动态
    private val myDynamicLiveData=MutableLiveData<Int>()
    val myDynamicList=Transformations.switchMap(myDynamicLiveData){result->
        Repository.getMyDynamic(result)
    }
    fun  setMyStudentId(id:Int){
        myDynamicLiveData.value=id
    }
    //删除动态
    private val removeLiveData=MutableLiveData<Int>()
    val removeResponse=Transformations.switchMap(removeLiveData){result->
        Repository.removeMyDynamic(result)
    }
    fun  setRemoveId(id:Int){
        removeLiveData.value=id
    }
    //获取某个动态的评论
    private val commentIdLiveData=MutableLiveData<Int >()
    val commentList=Transformations.switchMap(commentIdLiveData){result->
        Repository.getAllComment(result)
    }
    fun setDynamicId(id:Int){
        commentIdLiveData.value=id
    }
    //发布评论
    private val releaseCommentLiveData=MutableLiveData<DynamicComment>()
    val releaseCommentResponse=Transformations.switchMap(releaseCommentLiveData){result->
        Repository.releaseComment(result)
    }
    fun sendComment(dynamicComment: DynamicComment){
        releaseCommentLiveData.value=dynamicComment
    }
    //删除评论
    private val removeCommentLiveData=MutableLiveData<Int>()
    val removeCommentResponse=Transformations.switchMap(removeCommentLiveData){result->
        Repository.removeComment(result)
    }
    fun removeComment(id: Int){
        removeCommentLiveData.value=id
    }
    //是否点赞
    private val greatInfoLiveData=MutableLiveData<Dynamic>()
    val greatResponse=Transformations.switchMap(greatInfoLiveData){result->
        Repository.getSimpleGreat(result.sId,result.id)
    }
    fun inflateGreatInfo(dynamic: Dynamic){
        greatInfoLiveData.value=dynamic
    }
    //点赞/取消点赞
    private val greatStatusLiveData=MutableLiveData<Dynamic>()
    val greatStatusResponse=Transformations.switchMap(greatStatusLiveData){result->
        Repository.changeGreatStatus(result.sId,result.id)
    }
    fun inflateGreatStatusInfo(dynamic: Dynamic){
        greatStatusLiveData.value=dynamic
    }
    //获取单个动态根据dynamicId
    private val simpleDynamicLiveData=MutableLiveData<Int>()
    val simpleResponse=Transformations.switchMap(simpleDynamicLiveData){result->
        Repository.querySimpleOneDynamic(result)
    }
    fun setSimpleDynamicId(dynamicId:Int){
        simpleDynamicLiveData.value=dynamicId
    }
}