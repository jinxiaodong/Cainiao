package com.jarvis.home

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.jarvis.common.base.BaseFragment
import com.jarvis.home.databinding.FragmentHomeBinding

/**
 * @author jinxiaodong
 * @description：
 * @date 2021/8/25
 */
class HomeFragment : BaseFragment() {

    override fun getLayoutRes() = R.layout.fragment_home

    override fun bindView(view: View, savedInstanceState: Bundle?): ViewDataBinding {
        return FragmentHomeBinding.bind(view)
    }
}

