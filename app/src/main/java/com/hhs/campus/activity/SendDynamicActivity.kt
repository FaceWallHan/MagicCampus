package com.hhs.campus.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.util.Consumer
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import coil.load
import com.hhs.campus.AppClient
import com.hhs.campus.R
import com.hhs.campus.adapter.ShowImageAdapter
import com.hhs.campus.bean.Dynamic
import com.hhs.campus.bean.DynamicImage
import com.hhs.campus.bean.ImageShow
import com.hhs.campus.utils.OnSelectImageItemListener
import com.hhs.campus.utils.showToast
import com.hhs.campus.viewModel.DynamicViewModel
import com.hhs.campus.viewModel.StudentViewModel
import kotlinx.android.synthetic.main.activity_send_dynamic.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import java.io.File

class SendDynamicActivity : AppCompatActivity() ,TextWatcher,OnSelectImageItemListener,Consumer<Int>,
    java.util.function.Consumer<Int> {
    private val studentViewModel by lazy { ViewModelProvider(this).get(StudentViewModel::class.java) }
    private val dynamicViewModel by lazy { ViewModelProvider(this).get(DynamicViewModel::class.java) }
    private lateinit var submitItem:MenuItem
    private val imageList=ArrayList<ImageShow>()
    private val adapter=ShowImageAdapter(imageList,this)
    private val dynamic=Dynamic()
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
                    dynamic.sId=it.id
                }
            }
        })
        dynamic_content.addTextChangedListener(this)
        imageList.add(ImageShow("",true))
        val layoutManager= StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        dynamic_image_list.layoutManager=layoutManager
        adapter.onRemoveItemListener=this
        adapter.consumer=this
        dynamic_image_list.adapter=adapter
        dynamicViewModel.multipleImgResponse.observe(this, Observer { result->
            if (result.isSuccess){
                result.getOrNull()?.data?.fileName?.let {
                    dynamic.mark=it
                    dynamicViewModel.uploadDynamic(dynamic)
                }
            }
        })
        dynamicViewModel.dynamicResponse.observe(this, Observer { result->
            result.getOrNull()?.let {
                if (it.isSuccess()){
                    val intent=Intent()
                    setResult(Activity.RESULT_OK,intent)
                    finish()
                }else{
                    "发送失败".showToast()
                }
            }
        })
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.submit_dynamic->{
                if (imageList.size!=1){
                    //选择多张图片
                    val param=ArrayList<MultipartBody.Part>()
                    for (show in imageList) {
                        if (!show.isFirst){
                            val file= File(show.path)
                            val body=MultipartBody.create(MediaType.parse("image/*"),file)
                            val part=MultipartBody.Part.createFormData("fileName",file.name,body)
                            param.add(part)
                        }
                    }
                    dynamicViewModel.sendMultipleImg(param)
                }else{
                    //未选择
                    dynamicViewModel.uploadDynamic(dynamic)
                }
            }
            else->finish()
        }
        return true
    }
    private fun openPhotos(){
        val intent=Intent(this,MultipleImageActivity::class.java)
        intent.putExtra(AppClient.imageCount,9-(imageList.size-1))
        //限制选择图片数量为9
        startActivityForResult(intent,AppClient.chooseImage)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar,menu)
        submitItem= menu?.findItem(R.id.submit_dynamic)!!
        changeSubmitItemStatus(false)
        return true
    }

    override fun afterTextChanged(p0: Editable?) {
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        p0?.isEmpty()?.let { changeSubmitItemStatus(!it) }
        dynamic.content=p0.toString()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==AppClient.chooseImage&&resultCode== Activity.RESULT_OK){
            val extra = data?.getSerializableExtra(AppClient.imageList) as ArrayList<DynamicImage>
            if (imageList.size==1){
                imageList.remove(imageList[0])
            }else{
                imageList.remove(imageList[imageList.size-1])
            }
            for (image in extra) {
                imageList.add(ImageShow(image.path))
            }
            imageList.add(ImageShow("",true))
            changeSubmitItemStatus(imageList.size!=1)
            adapter.notifyDataSetChanged()
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

    override fun onItemClicked(position: Int, status: Boolean) {
        imageList.remove(imageList[position])
        changeSubmitItemStatus(imageList.size!=1)
        adapter.notifyDataSetChanged()
    }

    override fun accept(p0: Int) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
        }else{
            openPhotos()
        }
    }
    private fun changeSubmitItemStatus(status:Boolean){
        //当有文字或者有选择图片时才显示发送按钮
        submitItem.isVisible =status
    }

}

