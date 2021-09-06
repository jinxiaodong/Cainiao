package com.jarvis.mine.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
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

    private val args: UserInfoFragmentArgs by navArgs()

    override fun getLayoutRes() = R.layout.fragment_user_info

    override fun bindView(view: View, savedInstanceState: Bundle?): ViewDataBinding {
        return FragmentUserInfoBinding.bind(view).apply {
            //toolbar返回
            toolbarUserInfo.setNavigationOnClickListener { findNavController().navigateUp() }
            // save 返回
            btnSaveUserInfo.setOnClickListener { findNavController().navigateUp() }
            info = args.userInfo
        }
    }


}