package com.hhs.campus.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.hhs.campus.AppClient
import com.hhs.campus.R
import com.hhs.campus.activity.AppraiseRepairActivity
import com.hhs.campus.activity.MyRepairActivity
import com.hhs.campus.bean.Repair

class RepairAdapter(private val list: List<Repair>) :RecyclerView.Adapter<RepairAdapter.ViewHolder>(){
    inner class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val image:ImageView=itemView.findViewById(R.id.repair_item_image)
        val content:TextView=itemView.findViewById(R.id.repair_item_content)
        val area:TextView=itemView.findViewById(R.id.repair_item_area)
        val appraise:TextView=itemView.findViewById(R.id.repair_item_appraise)
        val schedule:TextView=itemView.findViewById(R.id.repair_item_schedule)
        val id:TextView=itemView.findViewById(R.id.repair_item_id)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context=parent.context
        val view= LayoutInflater.from(context).inflate(R.layout.show_repair_item,parent,false)
        val viewHolder = ViewHolder(view)
        view.setOnClickListener {
                val intent = Intent(context, MyRepairActivity::class.java)
                intent.putExtra(AppClient.repairId,list[viewHolder.adapterPosition])
                context.startActivity(intent)
        }
        viewHolder.appraise.setOnClickListener {
            if (list[viewHolder.adapterPosition].appraise==""){
                val intent = Intent(context, AppraiseRepairActivity::class.java)
                intent.putExtra(AppClient.repairId,list[viewHolder.adapterPosition].id)
                context.startActivity(intent)
            }
        }
        return viewHolder
    }

    override fun getItemCount()=list.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repair = list[position]
        holder.area.text=repair.repairArea
        holder.content.text=repair.content
        holder.schedule.text=repair.schedule
        holder.image.load(repair.image)
        //评分
        if (repair.schedule=="已完工"){
            holder.appraise.visibility=View.VISIBLE
            holder.appraise.text="去评价"
        }
        if (repair.appraise!=""){
            holder.appraise.text = repair.appraise+ "分"
        }
        holder.schedule.text=repair.schedule
        holder.id.text=repair.s_id

    }
}