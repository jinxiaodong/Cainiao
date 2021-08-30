package com.jarvis.login

import com.blankj.utilcode.util.ToastUtils
import com.jarvis.common.base.BaseActivity
import com.jarvis.login.databinding.ActivityLoginBinding
import com.jarvis.login.net.RegisterRsp
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author jinxiaodong
 * @description：登录界面
 * @date 2021/8/26
 */
class LoginActivity : BaseActivity<ActivityLoginBinding>() {


    private val viewModel: LoginViewModel by viewModel()

    override fun getLayoutRes() = R.layout.activity_login


    override fun initConfig() {
        super.initConfig()
        viewModel.apply {
            liveRegisterRsp.observerKt {
                if (it?.is_register == RegisterRsp.FLAG_IS_REGISTERED) {
                    repoLogin()
                }
            }
            liveLoginRsp.observerKt {
                ToastUtils.showShort("登录结果" + it.toString())
            }
        }
    }


    override fun initView() {
        super.initView()
        mBinding.apply {
            vm = viewModel
            toolbarLogin.setNavigationOnClickListener { finish() }
            tvRegisterLogin.setOnClickListener { ToastUtils.showShort("当前课程项目未实现注册账号功能!") }
        }
    }


    override fun initData() {
        super.initData()
    }
}