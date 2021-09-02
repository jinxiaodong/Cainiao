package com.jarvis.mine.ui

import androidx.lifecycle.MutableLiveData
import com.jarvis.common.base.BaseViewModel
import com.jarvis.mine.net.UserInfoRsp
import com.jarvis.service.repo.CniaoUserInfo

/**
 * @author jinxiaodong
 * @description：
 * @date 2021/9/2
 */
class MineViewModel :BaseViewModel() {


    //用在MineFragment中
    val liveUser = MutableLiveData<CniaoUserInfo>()

    //用在userInfoFragment中
    val liveInfo = MutableLiveData<UserInfoRsp>()

}