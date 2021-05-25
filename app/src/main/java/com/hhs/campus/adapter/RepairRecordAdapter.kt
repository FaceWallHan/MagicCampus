package com.hhs.campus.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.hhs.campus.R
import com.hhs.campus.bean.RepairRecord

class RepairRecordAdapter (private val list:List<RepairRecord>):RecyclerView.Adapter<RepairRecordAdapter.ViewHolder>(){
    inner class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val banner:ImageView=itemView.findViewById(R.id.repair_record_banner)
        val item:TextView=itemView.findViewById(R.id.repair_record_item)
        val time:TextView=itemView.findViewById(R.id.repair_record_time)
        val status:TextView=itemView.findViewById(R.id.repair_record_status)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.repair_record_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount()=list.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repairRecord = list[position]
        holder.time.text=repairRecord.time
        holder.status.text=repairRecord.status
        when(position){
            0->holder.banner.load(R.drawable.schedule_start)
            list.size-1->holder.banner.load(R.drawable.schedule_end)
            else->holder.banner.load(R.drawable.schedule_mid)
        }
        var itemText=""
        when(repairRecord.status){
            "受理"->itemText="管理员"+repairRecord.name+"已受理此维修订单"
            "派工"->itemText="管理员指派维修工"+repairRecord.name+"承修，维修工联系电话："+repairRecord.phone
            "签到"->itemText=repairRecord.name+"签到，维修工联系电话："+repairRecord.phone
            "已完工"->itemText="维修工"+repairRecord.name+"维修完工"
            "已评价"->itemText="用户"+repairRecord.name+"给出了评价，评价结果为"+repairRecord.phone+"分"
        }
        holder.item.text=itemText
    }
}