package com.hhs.campus.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hhs.campus.R
import com.hhs.campus.activity.ScanCodeActivity
import com.hhs.campus.activity.ShowRepairActivity
import com.hhs.campus.activity.WantRepairActivity

class OperateAdapter (private val list: List<String>): RecyclerView.Adapter<OperateAdapter.ViewHolder>(){
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val operate:TextView=itemView.findViewById(R.id.operate_repair)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context=parent.context
        val view=LayoutInflater.from(context).inflate(R.layout.operat_item_layout,parent,false)
        val viewHolder = ViewHolder(view)
        view.setOnClickListener {
            when(viewHolder.adapterPosition){
                0->context.startActivity(Intent(context,WantRepairActivity::class.java))
                1->context.startActivity(Intent(context,ShowRepairActivity::class.java))
                2->context.startActivity(Intent(context, ScanCodeActivity::class.java))
            }
        }
        return viewHolder
    }

    override fun getItemCount(): Int=list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item=list[position]
        holder.operate.text=item
    }
}