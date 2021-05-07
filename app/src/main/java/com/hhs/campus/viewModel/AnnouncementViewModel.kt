package com.hhs.campus.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.hhs.campus.Repository
import com.hhs.campus.bean.Student

class AnnouncementViewModel:ViewModel() {
    private val idLiveData=MutableLiveData<Student>()
    val listLiveDate=Transformations.switchMap(idLiveData){query->
        Repository.getSomeAnnouncement(query)
    }
    fun setStudentId(student: Student){
        idLiveData.value= student
    }
}