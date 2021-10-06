package com.hhs.campus.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hhs.campus.Repository
import com.hhs.campus.bean.ComplexMessage
import com.hhs.campus.net.ServiceCreator
import kotlinx.coroutines.flow.collect

class MessageViewModel:ViewModel() {
    val messageList=MutableLiveData<List<ComplexMessage>>()
    fun queryGreatAndCommentRecording(studentId:Int){
        ServiceCreator.startRequest(viewModelScope){
            Repository.queryGreatAndCommentRecording(studentId).collect { messageList.value=it.data }
        }
    }
}