package com.hhs.campus.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hhs.campus.RepositoryHilt
import com.hhs.campus.bean.Announcement
import com.hhs.campus.net.ServiceCreator
import kotlinx.coroutines.flow.collect

class AnnouncementViewModel @ViewModelInject constructor(val repositoryHilt: RepositoryHilt):ViewModel() {
    val listLiveData= MutableLiveData<List<Announcement>>()
    fun  requestSomeAnnouncement(){
        ServiceCreator.startRequest(viewModelScope){
            repositoryHilt.getSomeAnnouncement().collect {
                if (it.isSuccess()){
                    listLiveData.value=it.data
                }
            }
        }
    }
}