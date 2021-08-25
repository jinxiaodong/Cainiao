package com.jarvis.mine

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.jarvis.common.base.BaseFragment
import com.jarvis.mine.databinding.FragmentMineBinding

/**
 * @author jinxiaodong
 * @description：
 * @date 2021/8/25
 */
class MineFragment : BaseFragment() {

    override fun getLayoutRes() = R.layout.fragment_mine

    override fun bindView(view: View, savedInstanceState: Bundle?): ViewDataBinding {
        return FragmentMineBinding.bind(view)
    }
}