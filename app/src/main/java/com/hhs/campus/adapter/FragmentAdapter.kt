package com.hhs.campus.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentAdapter(activity: AppCompatActivity, private val list:List<Fragment>):FragmentStateAdapter(activity) {
    override fun getItemCount() =list.size

    override fun createFragment(position: Int)=list[position]
}