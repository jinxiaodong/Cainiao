package com.jarvis.common.widget

import android.view.MenuItem
import androidx.core.view.forEachIndexed
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * @author jinxiaodong
 * @description：BottomNavigationView和ViewPager2关联的中介者
 * @date 2021/8/26
 */
class BnvMediator(
    private val bnv: BottomNavigationView,
    private val viewPager2: ViewPager2,
    private val config: ((bnv: BottomNavigationView, viewpager: ViewPager2) -> Unit)? = null
) {

    private val map = mutableMapOf<MenuItem, Int>()

    init {
        bnv.menu.forEachIndexed { index, item ->
            map[item] = index
        }

    }

    fun attach() {
        config?.invoke(bnv, viewPager2)
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                bnv.selectedItemId = bnv.menu[position].itemId
            }
        })

        bnv.setOnNavigationItemSelectedListener {
            viewPager2.currentItem = map[it] ?: error("没有对应${it.title}的ViewPager2的元素")
            true
        }

    }
}