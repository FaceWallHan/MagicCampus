package com.hhs.campus.activity


import android.content.IntentFilter
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hhs.campus.R
import com.hhs.campus.adapter.FragmentAdapter
import com.hhs.campus.fragment.DynamicFragment
import com.hhs.campus.fragment.EatFragment
import com.hhs.campus.fragment.MineInfoFragment
import com.hhs.campus.fragment.RepairFragment
import com.hhs.campus.utils.NetChangeReceiver
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),BottomNavigationView.OnNavigationItemSelectedListener {
    private val receiver by lazy { NetChangeReceiver() }
    private val  itemId= mutableListOf(R.id.repair,R.id.eat,R.id.dynamic,R.id.mine)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        val filter=IntentFilter()
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        registerReceiver(receiver, filter)
        navMenu.setOnNavigationItemSelectedListener (this)
        val list= mutableListOf(RepairFragment(),EatFragment(),DynamicFragment(),MineInfoFragment())
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
    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        toolbar.title = item.title.toString()
        viewPager.setCurrentItem(itemId.indexOfFirst { it==item.itemId },true)
        return true
    }
}