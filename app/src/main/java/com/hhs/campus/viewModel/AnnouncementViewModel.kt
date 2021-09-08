package com.hhs.campus.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hhs.campus.Repository
import com.hhs.campus.bean.Announcement
import com.hhs.campus.net.ServiceCreator
import kotlinx.coroutines.flow.collect

class AnnouncementViewModel:ViewModel() {
    val listLiveData= MutableLiveData<List<Announcement>>()
    fun  requestSomeAnnouncement(){
        ServiceCreator.startRequest(viewModelScope){
            Repository.getSomeAnnouncement().collect {
                if (it.isSuccess()){
                    listLiveData.value=it.data
                }
            }
        }
    }



}