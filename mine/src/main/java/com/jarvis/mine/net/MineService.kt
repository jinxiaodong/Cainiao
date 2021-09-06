package com.jarvis.mine.net

import com.jarvis.service.network.BaseCniaoRsp
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

/**
 * @author jinxiaodong
 * @description：
 * @date 2021/9/3
 */
interface MineService {

    /**
     * 用户详情信息的获取
     */
    @GET("/member/userinfo")
    fun getUserInfo(@Header("token") token: String?): Call<BaseCniaoRsp>

}