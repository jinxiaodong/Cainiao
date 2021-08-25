package com.jarvis.cainiao

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.jarvis.cainiao.databinding.ActivityMainBinding
import com.jarvis.common.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getLayoutRes() = R.layout.activity_main


    override fun initConfig() {
        super.initConfig()
    }

    override fun initView() {
        super.initView()
        val findNavController = findNavController(R.id.nav_host_fragment)
        mBinding.navView.setupWithNavController(findNavController)
    }

    override fun initData() {
        super.initData()
    }


}

