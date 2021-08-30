package com.jarvis.login

import androidx.activity.viewModels
import com.jarvis.common.base.BaseActivity
import com.jarvis.login.databinding.ActivityLoginBinding

/**
 * @author jinxiaodong
 * @description：登录界面
 * @date 2021/8/26
 */
class LoginActivity : BaseActivity<ActivityLoginBinding>() {


    private val viewModel: LoginViewModel by viewModels { defaultViewModelProviderFactory }

    override fun getLayoutRes() = R.layout.activity_login


    override fun initConfig() {
        super.initConfig()
    }


    override fun initView() {
        super.initView()
        mBinding.apply {
            vm = viewModel
        }
    }


    override fun initData() {
        super.initData()
    }
}