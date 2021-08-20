package com.hhs.campus.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hhs.campus.R
import com.hhs.campus.activity.MyDynamicActivity
import com.hhs.campus.activity.ShowDynamicActivity
import com.hhs.campus.bean.Dynamic
import com.hhs.campus.databinding.DynamicItemBinding
import com.hhs.campus.utils.MultiImageView
import com.hhs.campus.utils.OnSelectImageItemListener

class ShowDynamicAdapter(private val list:List<Dynamic>, private val activity: Activity, val isMine:Boolean=false) :RecyclerView.Adapter<ShowDynamicAdapter.ViewHolder>(){
    lateinit var removedListener: OnSelectImageItemListener
    lateinit var urlClickListener: MultiImageView.OnItemClickListener
    inner class ViewHolder(val binding:DynamicItemBinding) :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=DataBindingUtil.inflate<DynamicItemBinding>(LayoutInflater.from(activity),R.layout.dynamic_item,parent,false)
        val holder=ViewHolder(binding)
        if (list.size>1||isMine){
            binding.root.setOnClickListener {
                val intent=Intent(activity,ShowDynamicActivity::class.java)
                intent.putExtra("dynamic",list[holder.adapterPosition])
                activity.startActivity(intent)
                if (activity is MyDynamicActivity){
                    activity.finish()
                }
            }
        }
        binding.dynamicItemRemove.setOnClickListener {
            removedListener.onItemClicked(list[holder.adapterPosition].id,false)
        }
        binding.dynamicItemImageList.setOnItemClickListener {url ->
            urlClickListener.onItemClick(url)
        }
        return holder
    }

    override fun getItemCount() =list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dynamic = list[position]
        val binding=holder.binding
        binding.dynamic=dynamic
        binding.adapter=this
        val convertString=dynamic.imagesList.map { item-> item.url }
        binding.dynamicItemImageList.setList(convertString)
    }

}