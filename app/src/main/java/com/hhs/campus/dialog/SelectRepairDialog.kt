package com.hhs.campus.dialog

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hhs.campus.R
import com.hhs.campus.viewModel.RepairViewModel
import kotlinx.android.synthetic.main.select_repair_layout.*

class SelectRepairDialog :DialogFragment(),View.OnClickListener ,AdapterView.OnItemSelectedListener{
    private val map=HashMap<String,ArrayList<String>>()
    private val repairViewModel by lazy { ViewModelProvider(requireActivity()).get(RepairViewModel::class.java)  }
    private  val groupList=ArrayList<String>()
    private  var isProject=false
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.select_repair_layout,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog?.window?.attributes?.windowAnimations=R.style.dialog_style
        determine_cancel.setOnClickListener(this)
        determine_repair.setOnClickListener(this)
        repairViewModel.projectList.observe(requireActivity(), Observer { result->
            if (result.isSuccess){
                result.getOrNull()?.let {
                    initSelectItem{
                        for (i in it.data){
                            addMapData(i.bigProject,i.smallProject)
                        }
                    }
                    isProject=true
                }
            }
        })
        repairViewModel.areaList.observe(requireActivity(), Observer { result->
            if (result.isSuccess){
                result.getOrNull()?.let {
                    initSelectItem{
                        for (i in it.data){
                            addMapData(i.bigArea,i.smallArea)
                        }
                    }
                }
            }
        })
    }
    private fun addMapData(bigObj:String,smallObj:String){
        if (map.containsKey(bigObj)){
            val list = map[bigObj]
            list?.add(smallObj)
        }else{
            val arr= ArrayList<String>()
            arr.add(smallObj)
            map[bigObj] = arr
        }
    }
    private fun initSelectItem(block:()->Unit){
        map.clear()
        groupList.clear()
        block()
        for (group in map){
            groupList.add(group.key)
        }
        val groupAdapter= activity?.let { ArrayAdapter(it,android.R.layout.simple_spinner_dropdown_item,groupList) }
        group_spinner.adapter=groupAdapter
        group_spinner.onItemSelectedListener=this
    }
    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.determine_cancel->dismiss()
            R.id.determine_repair->chooseType()
        }
        dismiss()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val childList=map[groupList[p2]]
        val childAdapter= activity?.let { ArrayAdapter(it,android.R.layout.simple_spinner_dropdown_item,childList!!)}
        child_spinner.adapter=childAdapter
    }
    private fun  chooseType(){
        if (isProject){
            repairViewModel.setSelectProjectRepair(child_spinner.selectedItem.toString())
        }else{
            repairViewModel.setSelectAreaRepair(child_spinner.selectedItem.toString())
        }
    }
    override fun onStart() {
        super.onStart()
        val dw = dialog?.window
        dw!!.setBackgroundDrawableResource(R.color.colorPrimary) //一定要设置背景
        val dm = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(dm)
        val params = dw.attributes
        //屏幕底部显示
        params.gravity = Gravity.CENTER
        params.width = dm.widthPixels //屏幕宽度
        params.height = dm.heightPixels / 5 //屏幕高度的1/3
        dw.attributes = params
    }
}