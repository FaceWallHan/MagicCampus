package com.hhs.campus.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.hhs.campus.R
import com.hhs.campus.viewModel.RepairViewModel
import kotlinx.android.synthetic.main.repair_details.*

class RepairDetailsFragment :Fragment(){
    private val viewModel by lazy { ViewModelProvider(requireActivity()).get(RepairViewModel::class.java) }
    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.repairLiveData.observe(requireActivity(), Observer { result->
            result.let {
                maintain_time.text=it.date
                maintain_status.text=it.schedule
                maintain_project.text=it.repairProject
                maintain_reserve_time.text=it.time
                maintain_content.text=it.content
                maintain_address.text=it.repairArea+it.address
                maintain_phone.text=it.phone
                if (it.image!=""){
                    repair_image.load(it.image)
                }else{
                    repair_image.load(R.drawable.repair_bg)
                }
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.repair_details,container,false)
    }
}