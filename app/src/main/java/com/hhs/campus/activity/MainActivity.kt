package com.hhs.campus.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
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
import com.hhs.campus.bean.Student
import com.hhs.campus.fragment.DynamicFragment
import com.hhs.campus.fragment.EatFragment
import com.hhs.campus.fragment.MoreFragment
import com.hhs.campus.fragment.RepairFragment
import com.hhs.campus.viewModel.StudentViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header.*
import kotlinx.android.synthetic.main.slide_layout.*

class MainActivity : AppCompatActivity() {
    private val viewModel by lazy { ViewModelProvider(this).get(StudentViewModel::class.java) }
    private val  itemId= mutableListOf(R.id.repair,R.id.eat,R.id.dynamic,R.id.more)
    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.slide_layout)
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.menu)
        }
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
        viewModel.refreshSelfInquire()
        viewModel.studentLocalLiveData.observe(this, Observer { result->
            if (result.isSuccess){
                val student = result.getOrNull()
                student?.let {
                    id_tv.text=it.id.toString()
                    name_tv.text=it.name
                    headIcon.load(it.avatar)
                }
            }
        })
        viewModel.saveLocalLiveData.observe(this, Observer { result->
            if (result.isSuccess){
                val intent= Intent(this,LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->drawerLayout.openDrawer(GravityCompat.START)
        }
        return true
    }
}