package com.hhs.campus.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hhs.campus.R
import com.hhs.campus.activity.ShowDynamicActivity
import com.hhs.campus.bean.Dynamic
import com.hhs.campus.utils.MultiImageView
import com.hhs.campus.utils.OnSelectImageItemListener
import de.hdodenhof.circleimageview.CircleImageView

class ShowDynamicAdapter(private val list:List<Dynamic>,  private val context:Context, private val isMine:Boolean=false) :RecyclerView.Adapter<ShowDynamicAdapter.ViewHolder>(){
    lateinit var removedListener: OnSelectImageItemListener
    lateinit var urlClickListener: MultiImageView.OnItemClickListener
    inner class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val head:CircleImageView=itemView.findViewById(R.id.dynamic_item_head)
        val content: TextView =itemView.findViewById(R.id.dynamic_item_content)
        val name: TextView =itemView.findViewById(R.id.dynamic_item_name)
        val remove: ImageView =itemView.findViewById(R.id.dynamic_item_remove)
        val time: TextView =itemView.findViewById(R.id.dynamic_item_time)
        val imageList: MultiImageView =itemView.findViewById(R.id.dynamic_item_image_list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.dynamic_item,parent,false)
        val holder=ViewHolder(view)
        view.setOnClickListener {
            val intent=Intent(context,ShowDynamicActivity::class.java)
            intent.putExtra("dynamic",list[holder.adapterPosition])
            context.startActivity(intent)
        }
        holder.remove.setOnClickListener {
            removedListener.onItemClicked(list[holder.adapterPosition].id,false)
        }
        holder.imageList.setOnItemClickListener {url ->
            urlClickListener.onItemClick(url)
        }

        return holder
    }

    override fun getItemCount() =list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dynamic = list[position]
        holder.content.text=dynamic.content
        holder.name.text=dynamic.name
        Glide.with(context).load(dynamic.avatar).into(holder.head)
            val multipleList=ArrayList<String>()
            for (item in dynamic.imagesList) {
                multipleList.add(item.url)
            }
        holder.imageList.setList(multipleList)
        holder.time.text=dynamic.time
        if (isMine){
            holder.remove.visibility=View.VISIBLE
        }
    }

}