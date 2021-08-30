package com.jarvis.login.repo

import androidx.lifecycle.LiveData
import com.jarvis.login.net.LoginReqBody
import com.jarvis.login.net.LoginRsp
import com.jarvis.login.net.RegisterRsp

/**
 * @author jinxiaodong
 * @description：登录模块的相关的抽象数据接口
 * @date 2021/8/30
 */
interface ILoginResource {

    //注册与否的检查结果
    val registerRsp: LiveData<RegisterRsp>

    //登录成功后的结果
    val loginRsp: LiveData<LoginRsp>


    /**
     * 校验手机号是否注册，合法
     */
    suspend fun checkRegister(mobi: String)


    /**
     * 手机号合法的基础上，调用登录，获取登录结果token
     */
    suspend fun requestLogin(body: LoginReqBody)

}