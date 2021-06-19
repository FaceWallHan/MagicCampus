package com.hhs.campus.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.hhs.campus.AppClient
import com.hhs.campus.R
import com.hhs.campus.activity.MyDynamicActivity
import com.hhs.campus.activity.SendDynamicActivity
import com.hhs.campus.adapter.ShowDynamicAdapter
import com.hhs.campus.bean.Dynamic
import com.hhs.campus.utils.MultiImageView
import com.hhs.campus.viewModel.DynamicViewModel
import kotlinx.android.synthetic.main.dynamic_layout.*

class DynamicFragment:Fragment() ,View.OnClickListener, MultiImageView.OnItemClickListener,SwipeRefreshLayout.OnRefreshListener{
    private val dynamicViewModel by lazy { ViewModelProvider(requireActivity()).get(DynamicViewModel::class.java) }
    private val list=ArrayList<Dynamic>()
    private  lateinit var  adapter:ShowDynamicAdapter
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initSome()
        dynamicViewModel.refreshAllDynamic()
        dynamicViewModel.allDynamicList.observe(requireActivity(), Observer { result->
            if (result.isSuccess){
                result.getOrNull()?.let {
                    list.clear()
                    list.addAll(it.data)
                    adapter.notifyDataSetChanged()
                }
            }
            refresh_dynamic.isRefreshing=false
        })
    }
    private fun initSome(){
        send_dynamic.setOnClickListener (this)
        my_dynamic.setOnClickListener (this)
        adapter=ShowDynamicAdapter(list, requireActivity())
        adapter.urlClickListener=this
        val layoutManager=LinearLayoutManager(requireActivity())
        layoutManager.orientation=LinearLayoutManager.VERTICAL
        dynamic_list.adapter=adapter
        dynamic_list.layoutManager=layoutManager
        refresh_dynamic.setColorSchemeResources(R.color.colorPrimary)
        refresh_dynamic.setOnRefreshListener (this)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dynamic_layout,container,false)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.my_dynamic->{
                 val intent=Intent(requireActivity(),MyDynamicActivity::class.java)
                startActivityForResult(intent, AppClient.remove)
            }
            R.id.send_dynamic->{
                val intent=Intent(requireActivity(),SendDynamicActivity::class.java)
                startActivityForResult(intent, AppClient.dynamic)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode== Activity.RESULT_OK){
            dynamicViewModel.refreshAllDynamic()
        }
    }

    override fun onItemClick(url: String?) {

    }

    override fun onRefresh() {
        dynamicViewModel.refreshAllDynamic()
    }
}