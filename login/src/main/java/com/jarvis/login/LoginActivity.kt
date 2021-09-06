package com.jarvis.login

import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.SPStaticUtils
import com.blankj.utilcode.util.ToastUtils
import com.jarvis.common.base.BaseActivity
import com.jarvis.common.ktx.context
import com.jarvis.common.net.config.SP_KEY_USER_TOKEN
import com.jarvis.common.utils.CniaoSpUtils
import com.jarvis.login.databinding.ActivityLoginBinding
import com.jarvis.login.net.RegisterRsp
import com.jarvis.service.repo.CniaoDbHelper
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author jinxiaodong
 * @description：登录界面
 * @date 2021/8/26
 */
@Route(path = "/login/loginActivity")
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
                it?.let {
                    CniaoSpUtils.put(SP_KEY_USER_TOKEN, it.token)
                    CniaoDbHelper.insertUserInfo(context, it)
                }
                finish()
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
        viewModel.obMobile.set("18895612587")
        viewModel.obPassword.set("qwerasdf123")
    }
}