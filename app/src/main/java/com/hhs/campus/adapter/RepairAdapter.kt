package com.hhs.campus.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hhs.campus.AppClient
import com.hhs.campus.R
import com.hhs.campus.activity.AppraiseRepairActivity
import com.hhs.campus.activity.MyRepairActivity
import com.hhs.campus.activity.ShowRepairActivity
import com.hhs.campus.bean.Repair
import com.hhs.campus.databinding.ShowRepairItemBinding

class RepairAdapter(private val list: List<Repair>, val activity: ShowRepairActivity) : RecyclerView.Adapter<RepairAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ShowRepairItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val binding = DataBindingUtil.inflate<ShowRepairItemBinding>(LayoutInflater.from(context), R.layout.show_repair_item, parent, false)
        val viewHolder = ViewHolder(binding)
        binding.root.setOnClickListener {
            val intent = Intent(context, MyRepairActivity::class.java)
            intent.putExtra(AppClient.repairId, list[viewHolder.layoutPosition])
            context.startActivity(intent)
        }
        binding.repairItemAppraise.setOnClickListener {
            val item = list[viewHolder.layoutPosition]
            val intent = Intent(context, AppraiseRepairActivity::class.java)
            intent.putExtra(AppClient.repairId, item.id)
            activity.startActivityForResult(intent, AppClient.appraise)
        }
        return viewHolder
    }

    override fun getItemCount() = list.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repair = list[position]
        holder.binding.repair = repair
    }
}