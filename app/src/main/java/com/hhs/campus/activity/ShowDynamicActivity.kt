package com.hhs.campus.activity

import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.hhs.campus.R
import com.hhs.campus.adapter.ShowCommentAdapter
import com.hhs.campus.adapter.ShowDynamicAdapter
import com.hhs.campus.bean.Dynamic
import com.hhs.campus.bean.DynamicComment
import com.hhs.campus.dialog.ShowImageDialog
import com.hhs.campus.utils.ImageUtil
import com.hhs.campus.utils.MultiImageView
import com.hhs.campus.utils.OnRemoveCommentListener
import com.hhs.campus.utils.showToast
import com.hhs.campus.viewModel.DynamicViewModel
import com.hhs.campus.viewModel.StudentViewModel
import kotlinx.android.synthetic.main.activity_show_dynamic.*

class ShowDynamicActivity : AppCompatActivity(), MultiImageView.OnItemClickListener ,
    OnRemoveCommentListener, TextWatcher,View.OnClickListener {
    private val studentViewModel by lazy { ViewModelProvider(this).get(StudentViewModel::class.java) }
    private val dynamicViewModel by lazy { ViewModelProvider(this).get(DynamicViewModel::class.java) }
    private val dialog=ShowImageDialog()
    private val commentList=ArrayList<DynamicComment>()
    private val adapter=ShowCommentAdapter(commentList)
    private val comment=DynamicComment()
    private lateinit var  dynamic:Dynamic
    private lateinit var  dynamicAdapter:ShowDynamicAdapter
    private val dynamicList=ArrayList<Dynamic>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_dynamic)
        initSome()
        studentViewModel.refreshSelfInquire()
        studentViewModel.studentLocalLiveData.observe(this, Observer { result->
            result.getOrNull()?.let {
                comment.sId=it.id
            }
        })
        dynamicViewModel.commentList.observe(this, Observer { result->
            if (result.isSuccess){
                result.getOrNull()?.data?.let {
                    commentList.clear()
                    commentList.addAll(it)
                    adapter.notifyDataSetChanged()
                }
            }
        })
        dynamicViewModel.releaseCommentResponse.observe(this, Observer { result->
            if (result.isSuccess){
                dynamicViewModel.setDynamicId(comment.dynamicId)
                dynamicViewModel.setSimpleDynamicId(dynamic.id)
                comment_et.setText("")
            }
        })
        dynamicViewModel.removeCommentResponse.observe(this, Observer { result->
            if (result.isSuccess){
                dynamicViewModel.setDynamicId(dynamic.id)
                dynamicViewModel.setSimpleDynamicId(dynamic.id)
            }
        })
        dynamicViewModel.inflateGreatInfo(dynamic)
        dynamicViewModel.greatResponse.observe(this, Observer { result->
            if (result.isSuccess){
                result.getOrNull()?.let {
                    if (it.isLike()){
                        changeGreat.setBackgroundResource(R.drawable.red_great)
                    }else if (it.isUnLike()){
                        changeGreat.setBackgroundResource(R.drawable.white_great)
                    }
                }
            }
        })
        dynamicViewModel.greatStatusResponse.observe(this, Observer { result->
            if (result.isSuccess){
                dynamicViewModel.inflateGreatInfo(dynamic)
                dynamicViewModel.setSimpleDynamicId(dynamic.id)
            }
        })
        dynamicViewModel.simpleResponse.observe(this, Observer { result->
            if (result.isSuccess){
                result.getOrNull()?.data?.let {
                    dynamic=it
                    dynamicList.clear()
                    dynamicList.add(dynamic)
                    dynamicAdapter.notifyDataSetChanged()
                }
            }
        })

    }
    private fun initSome(){
        dynamic = intent?.getSerializableExtra("dynamic") as Dynamic
         dynamicList.add(dynamic)
        dynamicAdapter=ShowDynamicAdapter(dynamicList,this)
        dynamicAdapter.urlClickListener=this
        adapter.idListener=this
        val groupAdapter=ConcatAdapter(dynamicAdapter,adapter)
        val layoutManager=LinearLayoutManager(this)
        layoutManager.orientation=LinearLayoutManager.VERTICAL
        dynamic_details_list.adapter=groupAdapter
        dynamic_details_list.layoutManager=layoutManager
        dynamicViewModel.setDynamicId(dynamic.id)
        comment.dynamicId=dynamic.id
        //
        comment_et.addTextChangedListener(this)
        send_comment.isEnabled=false
        send_comment.setOnClickListener (this)
        changeGreat.setOnClickListener(this)
    }
    override fun onItemClick(url: String?) {
        url?.let {
            dialog.show(supportFragmentManager,it)
        }
    }



    override fun afterTextChanged(p0: Editable?) {

    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        p0?.isEmpty()?.let { send_comment.isEnabled=!it}
        comment.content=p0.toString()
    }


    override fun onRemoveListen(commentId: Int, studentId: Int) {
        //删除我发布的动态下的评论或者我发布的评论
        if (dynamic.sId==comment.sId||studentId==comment.sId){
            ImageUtil.showAlertDialog(this,"确定要删除吗？",
                DialogInterface.OnClickListener { _, _ ->
                    dynamicViewModel.removeComment(commentId)
                },null)
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.changeGreat->dynamicViewModel.inflateGreatStatusInfo(dynamic)
            R.id.send_comment->{
                val textLength=comment.content.length
                if (textLength<2||textLength>50){
                    resources.getString(R.string.comment_hint).showToast()
                }else{
                    dynamicViewModel.sendComment(comment)
                }
            }
        }
    }

    override fun onBackPressed() {
        finish()
    }
}