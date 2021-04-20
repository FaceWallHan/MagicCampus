package com.hhs.campus


import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private val viewModel by lazy { ViewModelProvider(this).get(StudentViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        viewModel.refreshSelf()
        viewModel.statusLiveData.observe(this, Observer { result->
            if (result.isSuccess&&result.getOrNull()==true){
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
        })
        login.setOnClickListener {
            val id=student_id.text.toString().trim()
            val password=password.text.toString().trim()
            viewModel.login(id, password)
        }
        viewModel.studentLiveData.observe(this, Observer { result->
            if (result.isSuccess){
                Toast.makeText(this,"登录成功",Toast.LENGTH_LONG).show()
                result.getOrNull()?.let { viewModel.saveStudent(it) }
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }else{
                Toast.makeText(this,"登录失败",Toast.LENGTH_LONG).show()
            }
        })
        viewModel.studentLocalLiveData.observe(this, Observer {})
    }
}