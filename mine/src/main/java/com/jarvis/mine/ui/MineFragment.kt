package com.jarvis.mine.ui

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.navigation.fragment.findNavController
import com.alibaba.android.arouter.launcher.ARouter
import com.jarvis.common.base.BaseFragment
import com.jarvis.common.webview.WebActivity
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
                val info = viewModel.liveInfo.value
                if (info == null) {
                    ARouter.getInstance().build("/login/loginActivity").navigation()
                }else{
                    info.company = "测试"
                    val action =
                        MineFragmentDirections.actionMineFragmentToUserInfoFragment(info)
                    findNavController().navigate(action)
                }
            }
            tvOrdersMine.setOnClickListener {
                WebActivity.openUrl(requireContext(), "https://m.cniao5.com/user/order")
            }
            tvCouponMine.setOnClickListener {
                WebActivity.openUrl(requireContext(), "https://m.cniao5.com/user/coupon")
            }
            isvStudyCardMine.setOnClickListener {
                WebActivity.openUrl(requireContext(), "https://m.cniao5.com/sharecard")
            }
            isvShareSaleMine.setOnClickListener {
                WebActivity.openUrl(requireContext(), "https://m.cniao5.com/distribution")
            }
            isvGroupShoppingMine.setOnClickListener {
                WebActivity.openUrl(requireContext(), "https://m.cniao5.com/user/pintuan")
            }
            isvLikedCourseMine.setOnClickListener {
                WebActivity.openUrl(requireContext(), "https://m.cniao5.com/user/favorites")
            }
            isvFeedbackMine.setOnClickListener {
                WebActivity.openUrl(requireContext(), "https://cniao555.mikecrm.com/ktbB0ht")
            }
        }
    }


    override fun initData() {
        super.initData()
        CniaoDbHelper.getLiveUserInfo(requireContext()).observerKt {
            viewModel.getUserInfo(it?.token)
        }
    }

}