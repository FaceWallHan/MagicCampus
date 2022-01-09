package com.hhs.campus.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.hhs.campus.R
import com.hhs.campus.activity.MyDynamicActivity
import com.hhs.campus.activity.SendDynamicActivity
import com.hhs.campus.adapter.ShowDynamicAdapter
import com.hhs.campus.bean.Dynamic
import com.hhs.campus.dialog.ShowImageDialog
import com.hhs.campus.utils.MultiImageView
import com.hhs.campus.viewModel.DynamicViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dynamic_layout.*

@AndroidEntryPoint
class DynamicFragment : Fragment(), View.OnClickListener, MultiImageView.OnItemClickListener,
    SwipeRefreshLayout.OnRefreshListener {
    val dynamicViewModel by lazy { ViewModelProvider(requireActivity()).get(DynamicViewModel::class.java) }
    private val list = ArrayList<Dynamic>()
    private lateinit var adapter: ShowDynamicAdapter
    private val dialog = ShowImageDialog()
    private val requestLaunch=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == Activity.RESULT_OK) {
            dynamicViewModel.refreshAllDynamic()
        }
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initSome()
        dynamicViewModel.refreshAllDynamic()
        dynamicViewModel.allDynamicLiveData.observe(requireActivity(), Observer {
            list.clear()
            list.addAll(it)
            adapter.notifyDataSetChanged()
            refresh_dynamic.isRefreshing = false
        })
    }

    private fun initSome() {
        send_dynamic.setOnClickListener(this)
        my_dynamic.setOnClickListener(this)
        adapter = ShowDynamicAdapter(list, requireActivity())
        adapter.urlClickListener = this
        val layoutManager = LinearLayoutManager(requireActivity())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        dynamic_list.adapter = adapter
        dynamic_list.layoutManager = layoutManager
        refresh_dynamic.setColorSchemeResources(R.color.colorPrimary)
        refresh_dynamic.setOnRefreshListener(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dynamic_layout, container, false)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.my_dynamic -> {
                val intent = Intent(requireActivity(), MyDynamicActivity::class.java)
                requestLaunch.launch(intent)
            }
            R.id.send_dynamic -> {
                val intent = Intent(requireActivity(), SendDynamicActivity::class.java)
                requestLaunch.launch(intent)
            }
        }
    }

    override fun onItemClick(url: String?) {
        url?.let { dialog.show(childFragmentManager, it) }
    }

    override fun onRefresh() {
        dynamicViewModel.refreshAllDynamic()
    }
}