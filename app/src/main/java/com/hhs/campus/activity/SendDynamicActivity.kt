package com.hhs.campus.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.hhs.campus.AppClient
import com.hhs.campus.R
import com.hhs.campus.bean.DynamicImage
import com.hhs.campus.utils.showToast
import com.hhs.campus.viewModel.StudentViewModel
import kotlinx.android.synthetic.main.activity_send_dynamic.*

class SendDynamicActivity : AppCompatActivity() ,TextWatcher{
    private val studentViewModel by lazy { ViewModelProvider(this).get(StudentViewModel::class.java) }
    private lateinit var submitItem:MenuItem
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_dynamic)
        setSupportActionBar(send_dynamic_bar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        studentViewModel.refreshSelfInquire()
        studentViewModel.studentLocalLiveData.observe(this, Observer {result->
            if (result.isSuccess){
                result.getOrNull()?.let {
                    dynamic_head.load(it.avatar)
                    dynamic_name.text=it.name
                }
            }
        })
        dynamic_content.addTextChangedListener(this)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.submit_dynamic->{
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
                }else{
                    openPhotos()
                }

            }
            else->finish()
        }
        return true
    }
    private fun openPhotos(){
        val intent=Intent(this,MultipleImageActivity::class.java)
        startActivityForResult(intent,AppClient.chooseImage)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar,menu)
        submitItem= menu?.findItem(R.id.submit_dynamic)!!
        submitItem.isEnabled=false
        return true
    }

    override fun afterTextChanged(p0: Editable?) {
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        p0?.isEmpty()?.let { submitItem.isEnabled =!it }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==AppClient.chooseImage&&resultCode== Activity.RESULT_OK){
            val extra = data?.getSerializableExtra(AppClient.imageList) as ArrayList<DynamicImage>

        Log.d("1111111", "onActivityResult:?????$extra")
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            0 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openPhotos()
                } else {
                    "You denied the permission".showToast()
                }
            }
        }
    }

}

