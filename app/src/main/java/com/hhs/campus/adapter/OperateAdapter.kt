package com.hhs.campus.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hhs.campus.R
import com.hhs.campus.databinding.OperateItemLayoutBinding
import java.util.function.Consumer

class OperateAdapter (private val list: List<String>): RecyclerView.Adapter<OperateAdapter.ViewHolder>(){
    inner class ViewHolder(val binding: OperateItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)
    lateinit var itemListener: Consumer<Int>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context=parent.context
        val binding=DataBindingUtil.inflate<OperateItemLayoutBinding>(LayoutInflater.from(context),R.layout.operate_item_layout,parent,false)
        val viewHolder=ViewHolder(binding)
        binding.root.setOnClickListener {
            itemListener.accept(viewHolder.layoutPosition)
        }
        return viewHolder
    }

    override fun getItemCount(): Int=list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item=list[position]
        holder.binding.item=item
    }
}