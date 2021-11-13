package com.hhs.campus.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.hhs.campus.AppClient
import com.hhs.campus.R
import com.hhs.campus.adapter.FragmentAdapter
import com.hhs.campus.bean.Repair
import com.hhs.campus.fragment.RepairDetailsFragment
import com.hhs.campus.fragment.RepairRecordFragment
import com.hhs.campus.viewModel.RepairViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.my_repair.*

@AndroidEntryPoint
class MyRepairActivity : AppCompatActivity() {
    val viewModel by lazy { ViewModelProvider(this).get(RepairViewModel::class.java) }
    private val  itemId= mutableListOf(R.id.repair_details,R.id.repair_record)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_repair)
        setSupportActionBar(show_repair_detail)
        val repair = intent?.getSerializableExtra(AppClient.repairId) as Repair
        repair.let { viewModel.setRepairId(it) }
        val list= mutableListOf(RepairDetailsFragment(),RepairRecordFragment())
        val pagerAdapter= FragmentAdapter(this, list)
        repair_pager.adapter=pagerAdapter
        repair_pager.setCurrentItem(0,true)
        repair_pager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                nav_repair.selectedItemId=itemId[position]
            }
        })
        nav_repair.setOnNavigationItemSelectedListener { item ->
            repair_pager.setCurrentItem( itemId.indexOfFirst { it==item.itemId},true)
            true
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }


}