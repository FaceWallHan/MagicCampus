package com.hhs.campus.dialog

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.hhs.campus.R
import kotlinx.android.synthetic.main.show_image.*

class ShowImageDialog :DialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME,R.style.dialog_custom)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog?.window?.attributes?.windowAnimations=R.style.dialog_style
        Glide.with(requireContext()).load(tag).into(dynamic_image_dialog)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.show_image,container,false)
    }
    override fun onStart() {
        super.onStart()
        val dw = dialog?.window
        dw!!.setBackgroundDrawableResource(R.color.colorPrimary) //一定要设置背景
        val dm = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(dm)
        val params = dw.attributes
        //屏幕底部显示
        params.gravity = Gravity.CENTER
        params.width = (dm.widthPixels/ 1.2f).toInt()//屏幕宽度
        params.height = (dm.heightPixels /1.2f).toInt() //屏幕高度的1/3
        dw.attributes = params
    }
}