package com.jarvis.mine

import android.os.Bundle
import android.view.View
import com.jarvis.common.base.BaseFragment
import com.jarvis.mine.databinding.FragmentContainerMineBinding

/**
  * @author jinxiaodong
  * @description： Mine模块的主Fragment，用于内部navigation的容器
  * @date 2021/9/3
  */
class MineContainerFragment : BaseFragment() {

    override fun getLayoutRes() = R.layout.fragment_container_mine

    override fun bindView(view: View, savedInstanceState: Bundle?) =
        FragmentContainerMineBinding.bind(view)

}