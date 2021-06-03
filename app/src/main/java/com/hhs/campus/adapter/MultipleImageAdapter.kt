package com.hhs.campus.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hhs.campus.R
import com.hhs.campus.bean.DynamicImage
import com.hhs.campus.utils.OnSelectImageItemListener

class MultipleImageAdapter (val list:List<DynamicImage>,val context:Context):RecyclerView.Adapter<MultipleImageAdapter.ViewHolder>(){
    lateinit var onSelectImageItemListener: OnSelectImageItemListener
    inner class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val image: ImageView =itemView.findViewById(R.id.multiple_img)
        val select: CheckBox =itemView.findViewById(R.id.select_img)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.multiple_image_item,parent,false)
        val holder=ViewHolder(view)
        holder.select.setOnClickListener {
            val position=holder.adapterPosition
            val check=holder.select.isChecked
            list[position].isCheck=check
            onSelectImageItemListener.onItemClicked(position,check)
        }
        return holder
    }

    override fun getItemCount()=list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dynamicImage = list[position]
        Glide.with(context).load(dynamicImage.path).into(holder.image)
        holder.select.isChecked=dynamicImage.isCheck
    }
}