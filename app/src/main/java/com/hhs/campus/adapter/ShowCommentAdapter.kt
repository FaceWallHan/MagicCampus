package com.hhs.campus.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hhs.campus.R
import com.hhs.campus.bean.DynamicComment
import com.hhs.campus.databinding.CommentItemBinding
import com.hhs.campus.utils.OnRemoveCommentListener

class ShowCommentAdapter(private val list:List<DynamicComment>) :RecyclerView.Adapter<ShowCommentAdapter.ViewHolder>(){
    lateinit var idListener: OnRemoveCommentListener
    inner class ViewHolder(val binding:CommentItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=DataBindingUtil.inflate<CommentItemBinding>(LayoutInflater.from(parent.context),R.layout.comment_item,parent,false)
        val holder=ViewHolder(binding)
        binding.root.setOnLongClickListener {
            val item=list[holder.adapterPosition]
            idListener.onRemoveListen(item.id,item.sId)
            true
        }
        return holder
    }

    override fun getItemCount()=list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dynamicComment = list[position]
        holder.binding.comment=dynamicComment
    }
}