package com.hhs.campus.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hhs.campus.R
import com.hhs.campus.bean.ImageShow
import com.hhs.campus.databinding.ShowImageItemBinding
import com.hhs.campus.utils.OnSelectImageItemListener
import java.util.function.Consumer

class ShowImageAdapter(val list: List<ImageShow>, val context: Context) : RecyclerView.Adapter<ShowImageAdapter.ViewHolder>() {
    lateinit var onRemoveItemListener: OnSelectImageItemListener
    lateinit var consumer:Consumer<Int>

    inner class ViewHolder(val binding:ShowImageItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=DataBindingUtil.inflate<ShowImageItemBinding>(LayoutInflater.from(parent.context),R.layout.show_image_item, parent, false)
        val holder = ViewHolder(binding)
        binding.removeImg.setOnClickListener {
            onRemoveItemListener.onItemClicked(holder.adapterPosition, false)
        }
        binding.showImg.setOnClickListener {
            if (list[holder.adapterPosition].isFirst) {
                //传0无意义，只是为了回调而已
                //点击相机图片，去选择照片
                consumer.accept(0)
            }
        }
        return holder
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dynamicImage = list[position]
        holder.binding.show=dynamicImage
    }
}