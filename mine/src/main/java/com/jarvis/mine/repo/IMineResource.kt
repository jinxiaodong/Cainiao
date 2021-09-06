package com.jarvis.mine.repo

import androidx.lifecycle.LiveData
import com.jarvis.mine.net.UserInfoRsp

/**
 * @author jinxiaodong
 * @description：Mine 模块的数据获取接口
 * @date 2021/9/6
 */
interface IMineResource {


    /**
     * 用户信息的返回数据类liveData
     */
    val liveUserInfo: LiveData<UserInfoRsp>


    /**
     * 获取userInfo的api函数
     */
    suspend fun getUserInfo(token: String?)

}