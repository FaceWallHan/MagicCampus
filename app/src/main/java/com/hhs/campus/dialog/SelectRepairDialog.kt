package com.hhs.campus.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hhs.campus.R
import com.hhs.campus.utils.OtherUtils
import com.hhs.campus.viewModel.RepairViewModel
import kotlinx.android.synthetic.main.select_repair_layout.*


class SelectRepairDialog : DialogFragment(), View.OnClickListener,
    AdapterView.OnItemSelectedListener {
    private val map = HashMap<String, ArrayList<String>>()
    val repairViewModel by lazy { ViewModelProvider(requireActivity()).get(RepairViewModel::class.java) }
    private val groupList = ArrayList<String>()
    private var isProject = false
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.select_repair_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog?.window?.attributes?.windowAnimations = R.style.dialog_style
        determine_cancel.setOnClickListener(this)
        determine_repair.setOnClickListener(this)
        repairViewModel.repairProjectLiveData.observe(requireActivity(), Observer { result ->
            initSelectItem {
                for (i in result) {
                    addMapData(i.bigProject, i.smallProject)
                }
                isProject = true
            }
        })
        repairViewModel.repairAreaLiveData.observe(requireActivity(), Observer { result ->
            initSelectItem {
                for (i in result) {
                    addMapData(i.bigArea, i.smallArea)
                }
            }
        })
    }

    private fun addMapData(bigObj: String, smallObj: String) {
        if (map.containsKey(bigObj)) {
            val list = map[bigObj]
            list?.add(smallObj)
        } else {
            val arr = ArrayList<String>()
            arr.add(smallObj)
            map[bigObj] = arr
        }
    }

    private fun initSelectItem(block: () -> Unit) {
        map.clear()
        groupList.clear()
        block()
        for (group in map) {
            groupList.add(group.key)
        }
        val groupAdapter = activity?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_spinner_dropdown_item,
                groupList
            )
        }
        group_spinner.adapter = groupAdapter
        group_spinner.onItemSelectedListener = this
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.determine_cancel -> dismiss()
            R.id.determine_repair -> chooseType()
        }
        dismiss()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val childList = map[groupList[p2]]
        val childAdapter = activity?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_spinner_dropdown_item,
                childList!!
            )
        }
        child_spinner.adapter = childAdapter
    }

    private fun chooseType() {
        val child = child_spinner.selectedItem.toString()
        val repairSome = group_spinner.selectedItem.toString() + "-" + child
        if (isProject) {
            repairViewModel.setSelectProjectRepair(child)
        } else {
            repairViewModel.setSelectAreaRepair(repairSome)
        }
    }

    override fun onStart() {
        super.onStart()
        OtherUtils.siteDialog(dialog, activity) { params, dm ->
            params.width = dm.widthPixels
            params.height = dm.heightPixels / 5
        }
    }
}