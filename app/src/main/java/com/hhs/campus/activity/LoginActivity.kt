package com.hhs.campus.activity


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hhs.campus.R
import com.hhs.campus.utils.showToast
import com.hhs.campus.viewModel.StudentViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private val viewModel by lazy { ViewModelProvider(this).get(StudentViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        viewModel.refreshSelfInquire()
        viewModel.studentLocalLiveData.observe(this, Observer { result->
            if (result.isSuccess){
                val student = result.getOrNull()
                student?.let {
                    if (!it.isNull()){
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                }
            }
        })
        login.setOnClickListener {
            val id=student_id.text.toString().trim()
            val password=password.text.toString().trim()
            viewModel.login(id, password)
        }
        viewModel.studentLiveData.observe(this, Observer { result->
            if (result.isSuccess){
                "登录成功".showToast()
                result.getOrNull()?.let { viewModel.saveStudent(it) }
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }else{
                "登录失败".showToast()
            }
        })
        viewModel.saveLocalLiveData.observe(this, Observer {})
    }
}