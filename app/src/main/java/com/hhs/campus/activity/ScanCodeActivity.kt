package com.hhs.campus.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Vibrator
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import cn.bingoogolapple.qrcode.core.QRCodeView
import com.google.gson.Gson
import com.hhs.campus.AppClient
import com.hhs.campus.R
import com.hhs.campus.bean.Repair
import com.hhs.campus.utils.OtherUtils
import com.hhs.campus.utils.showToast
import kotlinx.android.synthetic.main.activity_scan_code.*

class ScanCodeActivity : AppCompatActivity(),QRCodeView.Delegate {
    private val requestPermission=registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){
        OtherUtils.checkPermissionMap(it){zbar_view.startSpotAndShowRect()}
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_code)
        zbar_view.setDelegate(this)
        OtherUtils.checkObtainPermission(requestPermission){
            zbar_view.startSpotAndShowRect()
        }
    }

    override fun onStart() {
        super.onStart()
        zbar_view.startCamera()
    }

    override fun onStop() {
        zbar_view.stopCamera()
        super.onStop()
    }

    override fun onDestroy() {
        zbar_view.onDestroy()
        super.onDestroy()
    }
    override fun onScanQRCodeSuccess(result: String?) {
        val  vibrator=getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(300)
        zbar_view.startSpot()
        Gson().fromJson(result, Repair::class.java)?.let {
            if (it.isCodeNull()){
                "请扫描正确的二维码".showToast()
            }else{
                val intent= Intent(this,WantRepairActivity::class.java)
                intent.putExtra(AppClient.repair,it)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onCameraAmbientBrightnessChanged(isDark: Boolean) {

    }

    override fun onScanQRCodeOpenCameraError() {

    }
}