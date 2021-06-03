package com.hhs.campus.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hhs.campus.R
import com.hhs.campus.activity.SendDynamicActivity
import kotlinx.android.synthetic.main.dynamic_layout.*

class DynamicFragment:Fragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        send_dynamic.setOnClickListener {
            val intent=Intent(requireActivity(),SendDynamicActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dynamic_layout,container,false)
    }
}