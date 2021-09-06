package com.jarvis.mine.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.LogUtils
import com.jarvis.common.net.support.serverData
import com.jarvis.mine.net.MineService
import com.jarvis.mine.net.UserInfoRsp
import com.jarvis.service.network.onBizError
import com.jarvis.service.network.onBizOK
import com.jarvis.service.network.onFailure
import com.jarvis.service.network.onSuccess

/**
 * @author jinxiaodong
 * @description：
 * @date 2021/9/6
 */
class MineRepo(private val service: MineService) : IMineResource {

    private val _userInfoRsp = MutableLiveData<UserInfoRsp>()

    override val liveUserInfo: LiveData<UserInfoRsp> = _userInfoRsp


    override suspend fun getUserInfo(token: String?) {
        service.getUserInfo(token)
            .serverData()
            .onSuccess {
                onBizError { code, message ->
                    LogUtils.w("获取用户信息 BizError $code,$message")
                }
                onBizOK<UserInfoRsp> { code, data, message ->
                    _userInfoRsp.value = data
                    LogUtils.w("获取用户信息 onBizOK $data")

                    return@onBizOK
                }
            }
            .onFailure {
                LogUtils.w("获取用户信息 接口异常 ${it.message}")
            }
    }
}