package com.hhs.campus.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hhs.campus.R
import com.hhs.campus.activity.ScanCodeActivity
import com.hhs.campus.activity.ShowRepairActivity
import com.hhs.campus.activity.WantRepairActivity
import com.hhs.campus.databinding.OperateItemLayoutBinding

class OperateAdapter (private val list: List<String>): RecyclerView.Adapter<OperateAdapter.ViewHolder>(){
    inner class ViewHolder(val binding: OperateItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context=parent.context
        val binding=DataBindingUtil.inflate<OperateItemLayoutBinding>(LayoutInflater.from(context),R.layout.operate_item_layout,parent,false)
        val viewHolder=ViewHolder(binding)
        binding.root.setOnClickListener {
            when(viewHolder.layoutPosition){
                0->context.startActivity(Intent(context, WantRepairActivity::class.java))
                1->context.startActivity(Intent(context, ShowRepairActivity::class.java))
                2->context.startActivity(Intent(context, ScanCodeActivity::class.java))
            }
        }
        return viewHolder
    }

    override fun getItemCount(): Int=list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item=list[position]
        holder.binding.item=item
    }
}