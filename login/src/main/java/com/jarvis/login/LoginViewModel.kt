package com.jarvis.login

import android.content.Context
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.blankj.utilcode.util.ToastUtils
import com.jarvis.common.base.BaseViewModel
import com.jarvis.login.net.LoginReqBody
import com.jarvis.login.repo.ILoginResource

/**
 * @author jinxiaodong
 * @description：
 * @date 2021/8/26
 */
class LoginViewModel(private val resource: ILoginResource) : BaseViewModel() {

    //账号，密码 的observable 对象
    val obMobile = ObservableField<String>()
    val obPassword = ObservableField<String>()


    val liveRegisterRsp = resource.registerRsp
    val liveLoginRsp = resource.loginRsp


    /**
     * 检查是否注册的账号
     */

    private fun checkRegister(mobi: String) = serverAwait {
        resource.checkRegister(mobi)
    }


    /**
     * 调用登录
     * val mobi: String = "18648957777",
     * val password: String = "cn5123456"
     */

    internal fun repoLogin() {
        val account = obMobile.get() ?: return
        val password = obPassword.get() ?: return
        serverAwait {
            resource.requestLogin(LoginReqBody(account, password))
        }
    }


    fun goLogin() {
        val account = obMobile.get() ?: return
        checkRegister(account)
    }


    fun wechat(ctx: Context) {
        ToastUtils.showShort("点击了微信登录")
    }

    fun qq(v: View) {
        ToastUtils.showShort("点击了QQ登录")
    }

    fun weibo() {
        ToastUtils.showShort("点击了微博登录")
    }

    fun AA(view: View) {
        ToastUtils.showShort("静态点击方式")
    }

}