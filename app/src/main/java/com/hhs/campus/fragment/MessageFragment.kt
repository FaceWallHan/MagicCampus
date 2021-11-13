package com.hhs.campus.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.hhs.campus.R
import com.hhs.campus.adapter.MessageAdapter
import com.hhs.campus.bean.ComplexMessage
import com.hhs.campus.databinding.MessageLayoutBinding
import com.hhs.campus.viewModel.MessageViewModel
import com.hhs.campus.viewModel.StudentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class MessageFragment:BaseViewFragment<MessageLayoutBinding>(), SwipeRefreshLayout.OnRefreshListener {
    val messageViewModel by lazy { ViewModelProvider(this).get(MessageViewModel::class.java) }
    val studentViewModel by lazy { ViewModelProvider(this).get(StudentViewModel::class.java) }
    private val list=ArrayList<ComplexMessage>()
    private val adapter=MessageAdapter(list)
    private var studentId by Delegates.notNull<Int>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initSome()
        studentViewModel.refreshSelfInquire()
        studentViewModel.inquireLiveData.observe(viewLifecycleOwner, Observer {
            studentId = it.id
            messageViewModel.queryGreatAndCommentRecording(studentId)
        })
        messageViewModel.messageList.observe(viewLifecycleOwner, Observer {
            list.clear()
            list.addAll(it)
            adapter.notifyDataSetChanged()
            binding.refreshMessage.isRefreshing=false
        })
    }
    private fun  initSome(){
        val layoutManager=LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.messageList.adapter=adapter
        binding.messageList.layoutManager=layoutManager
        binding.refreshMessage.setColorSchemeResources(R.color.colorPrimary)
        binding.refreshMessage.setOnRefreshListener(this)
    }
    override fun getSubLayoutId()=R.layout.message_layout
    override fun onRefresh() {
        messageViewModel.queryGreatAndCommentRecording(studentId)
    }
}