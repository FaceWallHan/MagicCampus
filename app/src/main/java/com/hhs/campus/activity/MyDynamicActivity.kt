package com.hhs.campus.activity

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hhs.campus.R
import com.hhs.campus.adapter.ShowDynamicAdapter
import com.hhs.campus.bean.Dynamic
import com.hhs.campus.dialog.ShowImageDialog
import com.hhs.campus.utils.ImageUtil
import com.hhs.campus.utils.MultiImageView
import com.hhs.campus.utils.OnSelectImageItemListener
import com.hhs.campus.viewModel.DynamicViewModel
import com.hhs.campus.viewModel.StudentViewModel
import kotlinx.android.synthetic.main.activity_my_dynamic.*

class MyDynamicActivity : AppCompatActivity(), OnSelectImageItemListener,
    MultiImageView.OnItemClickListener {
    private val studentModel by lazy { ViewModelProvider(this).get(StudentViewModel::class.java) }
    private val dynamicViewModel by lazy { ViewModelProvider(this).get(DynamicViewModel::class.java) }
    private val list = ArrayList<Dynamic>()
    private val adapter = ShowDynamicAdapter(list, this, true)
    private var isRemoveSuccess: Boolean = false
    private var studentId: Int = 0
    private val dialog = ShowImageDialog()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_dynamic)
        init()
        studentModel.refreshSelfInquire()
        studentModel.inquireLiveData.observe(this, Observer {
            studentId = it.id
            dynamicViewModel.setMyStudentId(it.id)
        })
        dynamicViewModel.myDynamicLiveData.observe(this, Observer {
            list.clear()
            list.addAll(it)
            adapter.notifyDataSetChanged()
        })
        dynamicViewModel.removeLiveData.observe(this, Observer {
            isRemoveSuccess = it.isSuccess()
            dynamicViewModel.setMyStudentId(studentId)
        })
    }

    private fun init() {
        adapter.urlClickListener = this
        adapter.removedListener = this
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        my_dynamic_list.layoutManager = layoutManager
        my_dynamic_list.adapter = adapter
    }

    override fun onItemClicked(position: Int, status: Boolean) {
        ImageUtil.showAlertDialog(this, "确定要删除吗？",
            DialogInterface.OnClickListener { _, _ ->
                dynamicViewModel.setRemoveId(position)
            }, null
        )
    }

    override fun onBackPressed() {
        if (isRemoveSuccess) {
            val intent = Intent()
            setResult(Activity.RESULT_OK, intent)
        }
        finish()
    }

    override fun onItemClick(url: String?) {
        url?.let { dialog.show(supportFragmentManager, it) }
    }
}