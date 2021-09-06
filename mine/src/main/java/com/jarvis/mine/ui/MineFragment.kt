package com.jarvis.mine.ui

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.navigation.fragment.findNavController
import com.alibaba.android.arouter.launcher.ARouter
import com.jarvis.common.base.BaseFragment
import com.jarvis.mine.R
import com.jarvis.mine.databinding.FragmentMineBinding
import com.jarvis.service.repo.CniaoDbHelper
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author jinxiaodong
 * @description：
 * @date 2021/8/25
 */
class MineFragment : BaseFragment() {


    private val viewModel: MineViewModel by viewModel()

    override fun getLayoutRes() = R.layout.fragment_mine

    override fun bindView(view: View, savedInstanceState: Bundle?): ViewDataBinding {
        return FragmentMineBinding.bind(view).apply {
            vm = viewModel
            //UI操作

            btnLogoutMine.setOnClickListener {
                CniaoDbHelper.deleteUserInfo(requireContext())
                ARouter.getInstance().build("/login/loginActivity").navigation()
            }

            //跳转userInfoFragment
            ivUserIconMine.setOnClickListener {
                val action = MineFragmentDirections.actionMineFragmentToUserInfoFragment()
                findNavController().navigate(action)
            }
        }
    }


    override fun initData() {
        super.initData()
        CniaoDbHelper.getLiveUserInfo(requireContext()).observerKt {
            viewModel.liveUser.value = it
        }
    }

}