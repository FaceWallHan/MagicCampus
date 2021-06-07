package com.hhs.campus.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hhs.campus.R
import com.hhs.campus.adapter.RepairRecordAdapter
import com.hhs.campus.viewModel.RepairViewModel
import kotlinx.android.synthetic.main.repair_record.*

class RepairRecordFragment :Fragment(){
    private val viewModel by lazy { ViewModelProvider(requireActivity()).get(RepairViewModel::class.java) }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.repairLiveData.observe(requireActivity(), Observer { result->
            viewModel.getRecordByRepairId(result.id.toString())
        })
        viewModel.repairRecord.observe(requireActivity(), Observer { result->
            if (result.isSuccess){
                val data = result.getOrNull()?.data
                data?.let {
                    if (it.isNullOrEmpty()){
                        record_empty.visibility= View.VISIBLE
                        repair_record_list.visibility=View.GONE
                    }else{
                        val adapter= RepairRecordAdapter(it)
                        val layoutManager= LinearLayoutManager(activity)
                        repair_record_list.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
                        repair_record_list.layoutManager=layoutManager
                        repair_record_list.adapter=adapter
                    }
                }
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.repair_record,container,false)
    }
}