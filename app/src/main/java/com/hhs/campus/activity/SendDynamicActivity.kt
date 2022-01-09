package com.hhs.campus.activity

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.Consumer
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import coil.load
import com.hhs.campus.AppClient
import com.hhs.campus.R
import com.hhs.campus.adapter.ShowImageAdapter
import com.hhs.campus.bean.Dynamic
import com.hhs.campus.bean.DynamicImage
import com.hhs.campus.bean.ImageShow
import com.hhs.campus.utils.ImageUtil
import com.hhs.campus.utils.OnSelectImageItemListener
import com.hhs.campus.utils.OtherUtils
import com.hhs.campus.utils.showToast
import com.hhs.campus.viewModel.DynamicViewModel
import com.hhs.campus.viewModel.StudentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_send_dynamic.*

@AndroidEntryPoint
class SendDynamicActivity : AppCompatActivity(), TextWatcher, OnSelectImageItemListener, Consumer<Int>,
    java.util.function.Consumer<Int> {
    val studentViewModel by lazy { ViewModelProvider(this).get(StudentViewModel::class.java) }
    val dynamicViewModel by lazy { ViewModelProvider(this).get(DynamicViewModel::class.java) }
    private lateinit var submitItem: MenuItem
    private val imageList = ArrayList<ImageShow>()
    private val adapter = ShowImageAdapter(imageList, this)
    private val dynamic = Dynamic()
    private val dialog by lazy { ProgressDialog(this) }
    private val requestDataLaunch=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result->
        if (result.resultCode== RESULT_OK){
            result.data?.let { changeImageCount(it) }
        }
    }
    private val requestPermission=registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){result->
        OtherUtils.checkPermissionMap(result){openPhotos()}
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_dynamic)
        setSupportActionBar(send_dynamic_bar)
        studentViewModel.refreshSelfInquire()
        studentViewModel.inquireLiveData.observe(this, Observer {
            dynamic_head.load(it.avatar)
            dynamic_name.text = it.name
            dynamic.sId = it.id
        })
        dialog.setMessage("loading...")
        dialog.setCancelable(false)
        dynamic_content.addTextChangedListener(this)
        imageList.add(ImageShow("", true))
        val layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        dynamic_image_list.layoutManager = layoutManager
        adapter.onRemoveItemListener = this
        adapter.consumer = this
        dynamic_image_list.adapter = adapter
        dynamicViewModel.multipleImgLiveData.observe(this, Observer {
            dynamic.mark = it.fileName
            dynamicViewModel.uploadDynamic(dynamic)
        })
        dynamicViewModel.dynamicLiveData.observe(this, Observer {
            if (it.isSuccess()) {
                val intent = Intent()
                setResult(Activity.RESULT_OK, intent)
                finish()
            } else {
                "发送失败".showToast()
            }
            dialog.dismiss()
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.submit_dynamic -> {
                if (imageList.size != 1) {
                    //选择多张图片
                    ImageUtil.uploadMultipleImage(imageList, this, lifecycleScope) {
                        dynamicViewModel.sendMultipleImg(it)
                    }
                } else {
                    //未选择
                    dynamicViewModel.uploadDynamic(dynamic)
                }
                dialog.show()
            }
            else -> finish()
        }
        return true
    }

    private fun openPhotos() {
        val intent = Intent(this, MultipleImageActivity::class.java)
        intent.putExtra(AppClient.imageCount, 9 - (imageList.size - 1))
        //限制选择图片数量为9
        requestDataLaunch.launch(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        submitItem = menu?.findItem(R.id.submit_dynamic)!!
        changeSubmitItemStatus(false)
        return true
    }

    override fun afterTextChanged(p0: Editable?) {
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        p0?.isEmpty()?.let { changeSubmitItemStatus(!it) }
        dynamic.content = p0.toString()
    }
    private fun changeImageCount(data: Intent){
        val extra = data.getSerializableExtra(AppClient.imageList) as ArrayList<DynamicImage>
        if (imageList.size == 1) {
            imageList.remove(imageList[0])
        } else {
            imageList.remove(imageList[imageList.size - 1])
        }
        for (image in extra) {
            imageList.add(ImageShow(image.path))
        }
        imageList.add(ImageShow("", true))
        changeSubmitItemStatus(imageList.size != 1)
        adapter.notifyDataSetChanged()
    }

    override fun onItemClicked(position: Int, status: Boolean) {
        imageList.remove(imageList[position])
        changeSubmitItemStatus(imageList.size != 1)
        adapter.notifyDataSetChanged()
    }

    override fun accept(p0: Int) {
        OtherUtils.checkObtainPermission(requestPermission){openPhotos()}
    }

    private fun changeSubmitItemStatus(status: Boolean) {
        //当有文字或者有选择图片时才显示发送按钮
        submitItem.isVisible = status
    }

}

