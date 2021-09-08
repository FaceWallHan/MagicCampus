package com.hhs.campus.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import com.hhs.campus.R

class NetChangeReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (!OtherUtils.isNetworkConnected()){
            ImageUtil.showAlertDialog(context,context.resources.getString(R.string.net_change),
                DialogInterface.OnClickListener { _, _ ->
                    context.startActivity(Intent(android.provider.Settings.ACTION_SETTINGS))
                },null)
        }
    }
}
