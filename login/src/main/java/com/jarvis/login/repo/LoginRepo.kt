package com.jarvis.login.repo

import androidx.lifecycle.LiveData
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SPStaticUtils
import com.blankj.utilcode.util.SpanUtils
import com.jarvis.common.model.SingleLiveData
import com.jarvis.common.net.config.SP_KEY_USER_TOKEN
import com.jarvis.common.net.support.serverData
import com.jarvis.login.net.LoginReqBody
import com.jarvis.login.net.LoginRsp
import com.jarvis.login.net.LoginService
import com.jarvis.login.net.RegisterRsp
import com.jarvis.service.network.onBizError
import com.jarvis.service.network.onBizOK
import com.jarvis.service.network.onFailure
import com.jarvis.service.network.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * @author jinxiaodong
 * @description：
 * @date 2021/8/30
 */

class LoginRepo(private val service: LoginService) : ILoginResource {

    private val _registerRsp = SingleLiveData<RegisterRsp>()
    private val _loginRsp = SingleLiveData<LoginRsp>()

    override val registerRsp: LiveData<RegisterRsp> = _registerRsp
    override val loginRsp: LiveData<LoginRsp> = _loginRsp


    override suspend fun checkRegister(mobi: String) {
        service.isRegister(mobi)
            .serverData()
            .onSuccess {
                //只要不是接口响应成功，
                onBizError { code, message ->
                    LogUtils.w("是否注册 BizError $code,$message")
                }
                onBizOK<RegisterRsp> { code, data, message ->
                    _registerRsp.value = data
                    LogUtils.i("是否注册 BizOK $data")
                    return@onBizOK
                }
            }.onFailure {
                LogUtils.e("是否注册 接口异常 ${it.message}")
            }
    }

    override suspend fun requestLogin(body: LoginReqBody) {
        service.login(body)
            .serverData()
            .onSuccess {
                //只要不是接口响应成功，
                onBizError { code, message ->
                    LogUtils.w("登录接口 BizError $code,$message")
                }
                onBizOK<LoginRsp> { code, data, message ->
                    _loginRsp.value = data
                    LogUtils.i("登录接口 BizOK $data")
                }
            }.onFailure {
                LogUtils.e("登录接口 异常 ${it.message}")
            }
    }
}