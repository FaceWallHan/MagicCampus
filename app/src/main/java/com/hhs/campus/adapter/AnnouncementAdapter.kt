package com.hhs.campus.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hhs.campus.R
import com.hhs.campus.activity.AnnouncementActivity
import com.hhs.campus.bean.Announcement
import com.hhs.campus.databinding.AnnouncementItemLayoutBinding

class AnnouncementAdapter (private val list:List<Announcement>):RecyclerView.Adapter<AnnouncementAdapter.ViewHolder>(){
    companion object{
        const val ANNOUNCEMENT_ITEM="ANNOUNCEMENT_ITEM"
    }
    inner class ViewHolder(val binding:AnnouncementItemLayoutBinding) :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context=parent.context
        val binding=DataBindingUtil.inflate<AnnouncementItemLayoutBinding>(LayoutInflater.from(context),R.layout.announcement_item_layout,parent,false)
        val holder=ViewHolder(binding)
        binding.root.setOnClickListener {
            val intent= Intent(context, AnnouncementActivity::class.java)
            intent.putExtra(ANNOUNCEMENT_ITEM,list[holder.layoutPosition])
            context.startActivity(intent)
        }
        return holder
    }

    override fun getItemCount() =list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.announcement=list[position]
    }
}