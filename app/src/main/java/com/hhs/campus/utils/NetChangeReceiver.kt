package com.hhs.campus.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NetChangeReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (!OtherUtils.isNetworkConnected()){
            ImageUtil.showNetAlertDialog(context)
        }
    }
}
