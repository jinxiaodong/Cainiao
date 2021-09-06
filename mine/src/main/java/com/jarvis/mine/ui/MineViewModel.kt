package com.jarvis.mine.ui

import androidx.lifecycle.MutableLiveData
import com.jarvis.common.base.BaseViewModel
import com.jarvis.mine.net.UserInfoRsp
import com.jarvis.mine.repo.IMineResource
import com.jarvis.service.repo.CniaoUserInfo

/**
 * @author jinxiaodong
 * @description：
 * @date 2021/9/2
 */
class MineViewModel(private val repo: IMineResource) : BaseViewModel() {



    //用在userInfoFragment中
    val liveInfo = repo.liveUserInfo

    fun getUserInfo(token: String?) {
        serverAwait {
            repo.getUserInfo(token)
        }
    }

}