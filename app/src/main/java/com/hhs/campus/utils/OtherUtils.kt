package com.hhs.campus.utils

import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.WindowManager
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.hhs.campus.AppClient
import com.hhs.campus.R
import com.hhs.campus.bean.ImageShow
import com.hhs.campus.bean.Repair

object OtherUtils {

    @JvmStatic
    @BindingAdapter("loadUrl")
    fun loadImage(view: ImageView, url: String) {
        //类似于给控件的扩展函数
        Glide.with(view.context)
            .load(url)
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("loadSpecialShow")
    fun loadSpecialShow(view: ImageView, imageShow: ImageShow) {
        if (imageShow.isFirst) {
            Glide.with(view.context).load(R.drawable.photo).into(view)
        } else {
            Glide.with(view.context).load(imageShow.path).into(view)
        }
    }

    @JvmStatic
    @BindingAdapter("loadSpecialRepair")
    fun loadSpecialRepair(view: ImageView, repair: Repair) {
        if (repair.image.isEmpty()) {
            Glide.with(view.context).load(R.drawable.repair_bg).into(view)
        } else {
            Glide.with(view.context).load(repair.image).into(view)
        }
    }

    fun siteDialog(dialog: Dialog?, activity: FragmentActivity?, block: (params: WindowManager.LayoutParams, dm: DisplayMetrics) -> Unit) {
        val dw = dialog?.window
        dw!!.setBackgroundDrawableResource(R.color.loginPageBackgroundColor) //一定要设置背景
        val dm = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(dm)
        val params = dw.attributes
        //屏幕底部显示
        params.gravity = Gravity.CENTER
        block(params, dm)//设置屏幕宽度高度
        dw.attributes = params
    }

    fun isNetworkConnected(): Boolean {
        val manager = AppClient.context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val work = manager.activeNetworkInfo
        work?.let {
            return work.isAvailable
        }
        return false
    }
    fun judgePermissionsResult(grantResults: IntArray,successOperate: () -> Unit){
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            successOperate()
        } else {
            "申请权限被拒绝".showToast()
        }
    }
}