package com.hhs.campus.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hhs.campus.AppClient
import com.hhs.campus.R
import com.hhs.campus.activity.ShowDynamicActivity
import com.hhs.campus.bean.ComplexMessage
import com.hhs.campus.databinding.MessageItemBinding

class MessageAdapter (val list:List<ComplexMessage>) :RecyclerView.Adapter<MessageAdapter.ViewHolder>(){
    inner class ViewHolder(val binding: MessageItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context=parent.context
        val binding= DataBindingUtil.inflate<MessageItemBinding>(LayoutInflater.from(context), R.layout.message_item,parent,false)
        val holder=ViewHolder(binding)
        binding.root.setOnClickListener {
            val intent= Intent(context, ShowDynamicActivity::class.java)
            intent.putExtra(AppClient.dynamicId,list[holder.layoutPosition].id)
            context.startActivity(intent)
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.message=list[position]
    }

    override fun getItemCount()=list.size
}