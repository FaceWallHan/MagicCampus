package com.hhs.campus.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.hhs.campus.R
import com.hhs.campus.bean.DynamicComment
import com.hhs.campus.utils.OnRemoveCommentListener
import de.hdodenhof.circleimageview.CircleImageView

class ShowCommentAdapter(private val list:List<DynamicComment>) :RecyclerView.Adapter<ShowCommentAdapter.ViewHolder>(){
    lateinit var idListener: OnRemoveCommentListener
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val header:CircleImageView=itemView.findViewById(R.id.comment_head)
        val name:TextView=itemView.findViewById(R.id.comment_name)
        val time:TextView=itemView.findViewById(R.id.comment_time)
        val content:TextView=itemView.findViewById(R.id.comment_content)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.comment_item,parent,false)
        val holder=ViewHolder(view)
        view.setOnLongClickListener {
            idListener.onRemoveListen(list[holder.adapterPosition].id,list[holder.adapterPosition].sId)
            true
        }
        return holder
    }

    override fun getItemCount()=list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dynamicComment = list[position]
        holder.name.text=dynamicComment.name
        holder.time.text=dynamicComment.time
        holder.content.text=dynamicComment.content
        holder.header.load(dynamicComment.avatar)
    }
}