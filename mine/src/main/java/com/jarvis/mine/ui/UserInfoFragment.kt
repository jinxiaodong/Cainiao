package com.jarvis.mine.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.navigation.fragment.findNavController
import com.jarvis.common.base.BaseFragment
import com.jarvis.mine.R
import com.jarvis.mine.databinding.FragmentUserInfoBinding
import com.jarvis.service.repo.CniaoDbHelper
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author jinxiaodong
 * @description：
 * @date 2021/9/3
 */
class UserInfoFragment : BaseFragment() {

    private val viewModel: MineViewModel by viewModel()


    override fun getLayoutRes() = R.layout.fragment_user_info

    override fun bindView(view: View, savedInstanceState: Bundle?): ViewDataBinding {
        return FragmentUserInfoBinding.bind(view).apply {

            vm = viewModel
            //toolbar返回
            toolbarUserInfo.setNavigationOnClickListener { findNavController().navigateUp() }
            toolbarUserInfo.navigationIcon?.setTint(Color.WHITE)
            // save 返回
            btnSaveUserInfo.setOnClickListener { findNavController().navigateUp() }

        }
    }

    override fun initData() {
        super.initData()
        CniaoDbHelper.getLiveUserInfo(requireContext()).observerKt {
            //获取用户的userInfo的接口数据
        }
    }
}