package com.hhs.campus.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hhs.campus.RepositoryHilt
import com.hhs.campus.bean.ComplexMessage
import com.hhs.campus.net.ServiceCreator
import kotlinx.coroutines.flow.collect

class MessageViewModel @ViewModelInject constructor(val repositoryHilt: RepositoryHilt):ViewModel() {
    val messageList=MutableLiveData<List<ComplexMessage>>()
    fun queryGreatAndCommentRecording(studentId:Int){
        ServiceCreator.startRequest(viewModelScope){
            repositoryHilt.queryGreatAndCommentRecording(studentId).collect { messageList.value=it.data }
        }
    }
}