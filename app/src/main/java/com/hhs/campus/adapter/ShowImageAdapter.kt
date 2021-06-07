package com.hhs.campus.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hhs.campus.R
import com.hhs.campus.bean.ImageShow
import com.hhs.campus.utils.OnSelectImageItemListener
import java.util.function.Consumer

class ShowImageAdapter(val list: List<ImageShow>, val context: Context) :
    RecyclerView.Adapter<ShowImageAdapter.ViewHolder>() {
    lateinit var onRemoveItemListener: OnSelectImageItemListener
    lateinit var consumer:Consumer<Int>

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.show_img)
        val remove: ImageView = itemView.findViewById(R.id.remove_img)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.show_image_item, parent, false)
        val holder = ViewHolder(view)
        holder.remove.setOnClickListener {
            onRemoveItemListener.onItemClicked(holder.adapterPosition, false)
        }
        holder.image.setOnClickListener {
            if (list[holder.adapterPosition].isFirst) {
                consumer.accept(0)
            }
        }
        return holder
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dynamicImage = list[position]
        if (dynamicImage.isFirst) {
            holder.remove.visibility = View.GONE
            Glide.with(context).load(R.drawable.photo).into(holder.image)
        } else {
            holder.remove.visibility = View.VISIBLE
            Glide.with(context).load(dynamicImage.path).into(holder.image)
        }
    }
}