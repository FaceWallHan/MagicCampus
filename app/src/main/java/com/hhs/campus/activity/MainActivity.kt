package com.hhs.campus.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import coil.load
import com.hhs.campus.R
import com.hhs.campus.adapter.FragmentAdapter
import com.hhs.campus.bean.ImageHeader
import com.hhs.campus.bean.Student
import com.hhs.campus.dialog.ImageDialog
import com.hhs.campus.fragment.DynamicFragment
import com.hhs.campus.fragment.EatFragment
import com.hhs.campus.fragment.MoreFragment
import com.hhs.campus.fragment.RepairFragment
import com.hhs.campus.utils.FileUtil
import com.hhs.campus.utils.ImageUtil
import com.hhs.campus.utils.OnAddPictureListener
import com.hhs.campus.utils.showToast
import com.hhs.campus.viewModel.StudentViewModel
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header.*
import kotlinx.android.synthetic.main.slide_layout.*
import java.io.File

class MainActivity : AppCompatActivity(), OnAddPictureListener {
    private val viewModel by lazy { ViewModelProvider(this).get(StudentViewModel::class.java) }
    private val  itemId= mutableListOf(R.id.repair,R.id.eat,R.id.dynamic,R.id.more)
    private val imageDialog  by lazy {
        val dialog= ImageDialog()
        dialog.addPictureListener=this
        dialog}
    private lateinit var student:Student
    private lateinit var fileName:String
    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.slide_layout)
        setSupportActionBar(toolbar)
        observerSomeThing()
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.menu)
        }
        addHeaderLayout()
        navMenu.setOnNavigationItemSelectedListener { item ->
            toolbar.title = item.title.toString()
            viewPager.setCurrentItem(itemId.indexOfFirst { it==item.itemId },true)
            true
        }
        navView.setNavigationItemSelectedListener {item->
            when(item.itemId){
                R.id.exit->AlertDialog.Builder(this)
                    .setMessage("确定退出吗？")
                    .setNegativeButton("取消",null)
                    .setNeutralButton("确定"){ _, _ -> viewModel.saveStudent(Student()) }
                    .show()
            }
            if (item.itemId!=R.id.exit){
                drawerLayout.closeDrawers()
            }
            true
        }
        val list= mutableListOf(RepairFragment(),EatFragment(),DynamicFragment(),MoreFragment())
        val pagerAdapter= FragmentAdapter(this, list)
        viewPager.adapter=pagerAdapter
        viewPager.setCurrentItem(0,true)
        viewPager.registerOnPageChangeCallback(object:ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                navMenu.selectedItemId=itemId[position]
            }
        })
    }
    private fun addHeaderLayout(){
        val headView=navView.inflateHeaderView(R.layout.nav_header)
        val head=headView.findViewById<CircleImageView>(R.id.headIcon)
        head.setOnClickListener {imageDialog.show(supportFragmentManager,"") }
    }
    private fun observerSomeThing(){
        viewModel.saveLocalLiveData.observe(this, Observer { result->
            if (result.isSuccess){
                val intent= Intent(this,LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
        viewModel.refreshSelfInquire()
        viewModel.studentLocalLiveData.observe(this, Observer { result->
            if (result.isSuccess){
                result.getOrNull()?.let {
                    student = it
                }
                student.let {
                    id_tv.text=it.id.toString()
                    name_tv.text=it.name
                    headIcon.load(it.avatar)
                }
            }
        })
        viewModel.imageUrl.observe(this, Observer { result->
            if (result.isSuccess){
                result.getOrNull()?.let {
                    fileName=it.fileName
                    //上传成功获取imageUrl
                    Log.d("111111111111111", "observerSomeThing: $fileName")
                    viewModel.updateHead(ImageHeader(student.id,fileName))
                }
                Log.d("111111111111111", "imageUrl?????:")
            }
                Log.d("111111111111111", "imageUrl????$result")
        })
        viewModel.headResponse.observe(this, Observer { result->
            if (result.isSuccess){
                student.avatar=fileName
                viewModel.saveStudent(student)
                "更改成功".showToast()
            }else{
                "更改失败".showToast()
            }
        })
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->drawerLayout.openDrawer(GravityCompat.START)
        }
        return true
    }

    override fun choosePicture(data: Uri) {
        val bitmap= ImageUtil.getBitmapFromUri(data,this)
        headIcon.setImageBitmap(bitmap)
        imageDialog.dismiss()
        val fileName = FileUtil.getPath(this,data)
        ImageUtil.uploadLocalImage(File(fileName),this){ part->
            viewModel.uploadFile(part)
        }
    }

    override fun takePicture(imageUri: Uri, file: File) {
        val bitmap= BitmapFactory.decodeStream(contentResolver.openInputStream(imageUri))
        headIcon.setImageBitmap(ImageUtil.rotateIfRequired(bitmap,file))
        imageDialog.dismiss()
        ImageUtil.uploadLocalImage(file,this){ part->
            viewModel.uploadFile(part)
        }
    }
}