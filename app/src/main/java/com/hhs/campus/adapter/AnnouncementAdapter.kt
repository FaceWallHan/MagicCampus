package com.hhs.campus.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.hhs.campus.R
import com.hhs.campus.activity.AnnouncementActivity
import com.hhs.campus.bean.Announcement

class AnnouncementAdapter (private val list:List<Announcement>):RecyclerView.Adapter<AnnouncementAdapter.ViewHolder>(){
    companion object{
        const val ANNOUNCEMENT_ITEM="ANNOUNCEMENT_ITEM"
    }
    inner class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val img:ImageView=itemView.findViewById(R.id.announcement_item_img)
        val title:TextView=itemView.findViewById(R.id.announcement_item_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context=parent.context
        val view=LayoutInflater.from(context).inflate(R.layout.announcement_item_layout,parent,false)
        val holder=ViewHolder(view)
        holder.itemView.setOnClickListener {
            val item=list[holder.adapterPosition]
            val intent=Intent(context,AnnouncementActivity::class.java)
            intent.putExtra(ANNOUNCEMENT_ITEM,item)
            context.startActivity(intent)
        }
        return holder
    }

    override fun getItemCount() =list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item=list[position]
        holder.title.text=item.title
        holder.img.load(item.image)
//        Glide.with(context).load(item.image).into(holder.img)
    }
}