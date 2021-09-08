package com.hhs.campus.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Vibrator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import cn.bingoogolapple.qrcode.core.QRCodeView
import com.google.gson.Gson
import com.hhs.campus.AppClient
import com.hhs.campus.R
import com.hhs.campus.bean.Repair
import com.hhs.campus.utils.OtherUtils
import com.hhs.campus.utils.showToast
import kotlinx.android.synthetic.main.activity_scan_code.*

class ScanCodeActivity : AppCompatActivity(),QRCodeView.Delegate {
    private val permission=1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_code)
        zbar_view.setDelegate(this)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            zbar_view.startSpotAndShowRect()
        }else{
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE), permission)
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            permission -> OtherUtils.judgePermissionsResult(grantResults){zbar_view.startSpotAndShowRect()}
        }
    }
}