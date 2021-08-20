package com.hhs.campus.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.hhs.campus.R
import com.hhs.campus.bean.RepairRecord
import com.hhs.campus.databinding.RepairRecordItemBinding
import com.hhs.campus.utils.RepairStatus

class RepairRecordAdapter (private val list:List<RepairRecord>):RecyclerView.Adapter<RepairRecordAdapter.ViewHolder>(){
    inner class ViewHolder(val binding:RepairRecordItemBinding) :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=DataBindingUtil.inflate<RepairRecordItemBinding>(LayoutInflater.from(parent.context),R.layout.repair_record_item,parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount()=list.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repairRecord = list[position]
        val binding = holder.binding
        binding.record=repairRecord
        val drawableId= when(position){
            0-> R.drawable.schedule_start
            list.size-1-> R.drawable.schedule_end
            else-> R.drawable.schedule_mid
        }
        val context=binding.root.context
        binding.repairRecordBanner.load(drawableId)
        val itemText=when(repairRecord.status){
            RepairStatus.ACCEPT.status->formatResource(context,R.string.accept,repairRecord.name)
            RepairStatus.DISPATCH.status->formatResource(context,R.string.dispatch,repairRecord.name,repairRecord.phone)
            RepairStatus.SIGN.status->formatResource(context,R.string.sign,repairRecord.name,repairRecord.phone)
            RepairStatus.COMPLETED.status->formatResource(context,R.string.completed,repairRecord.name,repairRecord.phone)
            RepairStatus.EVALUATED.status->formatResource(context,R.string.evaluated,repairRecord.name,repairRecord.phone)
            else->""
        }
        binding.repairRecordItem.text=itemText
    }
    private fun formatResource(context: Context,strId:Int,vararg args: Any?):String{
        val string = context.resources.getString(strId)
        return String.format(string,*args)
    }
}