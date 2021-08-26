package com.jarvis.cainiao

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.jarvis.cainiao.databinding.ActivityMainBinding
import com.jarvis.common.base.BaseActivity
import com.jarvis.common.widget.BnvMediator
import com.jarvis.course.CourseFragment
import com.jarvis.home.HomeFragment
import com.jarvis.mine.MineFragment
import com.jarvis.study.StudyFragment

class MainActivity : BaseActivity<ActivityMainBinding>() {

    companion object {
        private const val INDEX_HOME = 0//对应bottomNavigationView的tab的index
        private const val INDEX_COURSE = 1//课程
        private const val INDEX_STUDY = 2//学习中心
        private const val INDEX_MINE = 3//我的
    }


    override fun getLayoutRes() = R.layout.activity_main


    private val fragments = mapOf<Int, Fragment>(
        INDEX_HOME to HomeFragment(),
        INDEX_COURSE to CourseFragment(),
        INDEX_STUDY to StudyFragment(),
        INDEX_MINE to MineFragment()
    )

    override fun initConfig() {
        super.initConfig()
    }

    override fun initView() {
        super.initView()
        mBinding.apply {
            viewpager.adapter = MainViewPagerAdapter(this@MainActivity, fragments)
            BnvMediator(navView, viewpager) { bnv, viewpager ->
                viewpager.isUserInputEnabled = false
            }.attach()
        }
    }


    override fun initData() {
        super.initData()
    }

}


class MainViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val fragments: Map<Int, Fragment>
) :
    FragmentStateAdapter(fragmentActivity) {


    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position] ?: error("111")
    }


}

