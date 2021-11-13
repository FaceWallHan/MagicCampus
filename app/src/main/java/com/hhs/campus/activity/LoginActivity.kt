package com.hhs.campus.activity


import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hhs.campus.R
import com.hhs.campus.bean.Login
import com.hhs.campus.databinding.ActivityLoginBinding
import com.hhs.campus.utils.ImageUtil
import com.hhs.campus.utils.OtherUtils
import com.hhs.campus.utils.showToast
import com.hhs.campus.viewModel.StudentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_login.*

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val dialog by lazy { ProgressDialog(this) }
    val viewModel by lazy { ViewModelProvider(this).get(StudentViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login)
        binding.loginBean = Login("20210001","123456")
        dialog.setCancelable(false)
        dialog.setMessage("loading...")
        viewModel.refreshSelfInquire()
        viewModel.inquireLiveData.observe(this, Observer {
            if (!it.isNull()) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        })
        login.setOnClickListener {
            binding.loginBean?.let {
                if (it.isNotNull()) {
                    if (OtherUtils.isNetworkConnected()){
                        viewModel.login(it)
                        dialog.show()
                    }else{
                        ImageUtil.showNetAlertDialog(this)
                    }
                } else {
                    ImageUtil.showAlertDialog(this, "学号或密码不能为空！", null, null)
                }
            }
        }
        viewModel.loginLiveData.observe(this, Observer { result ->
            if (result.isSuccess()) {
                "登录成功".showToast()
                viewModel.saveStudent(result.data)
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                "登录失败".showToast()
            }
            dialog.dismiss()
        })
        viewModel.saveLiveData.observe(this, Observer {})
    }
}