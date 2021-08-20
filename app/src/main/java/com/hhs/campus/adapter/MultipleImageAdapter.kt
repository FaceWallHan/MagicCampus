package com.hhs.campus.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hhs.campus.R
import com.hhs.campus.bean.DynamicImage
import com.hhs.campus.databinding.MultipleImageItemBinding
import com.hhs.campus.utils.OnSelectImageItemListener

class MultipleImageAdapter (val list:List<DynamicImage>, val context:Context,  val showCheck:Boolean=true):RecyclerView.Adapter<MultipleImageAdapter.ViewHolder>(){
    lateinit var onSelectImageItemListener: OnSelectImageItemListener
    inner class ViewHolder(val binding:MultipleImageItemBinding) :RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=DataBindingUtil.inflate<MultipleImageItemBinding>(LayoutInflater.from(parent.context),R.layout.multiple_image_item,parent,false)
        val holder=ViewHolder(binding)
        binding.selectImg.setOnClickListener {
            val position=holder.adapterPosition
            val check=binding.selectImg.isChecked
            list[position].isCheck=check
            onSelectImageItemListener.onItemClicked(position,check)
        }
        return holder
    }

    override fun getItemCount()=list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dynamicImage = list[position]
        val bind= holder.binding
        bind.adapter=this
        bind.image=dynamicImage
    }
}