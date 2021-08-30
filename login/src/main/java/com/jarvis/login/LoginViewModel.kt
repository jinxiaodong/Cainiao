package com.jarvis.login

import android.content.Context
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.blankj.utilcode.util.ToastUtils

/**
 * @author jinxiaodong
 * @description：
 * @date 2021/8/26
 */
class LoginViewModel : ViewModel() {

    //账号，密码 的observable 对象
    val obMobile = ObservableField<String>()
    val obPassword = ObservableField<String>()


    fun goLogin() {
        val account = obMobile.get() ?: return
        val pwd = obPassword.get() ?: return
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