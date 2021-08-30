package com.jarvis.login.net

import com.jarvis.service.network.BaseCniaoRsp
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * @author jinxiaodong
 * @description：登录模块接口
 * @date 2021/8/27
 */
interface LoginService {

    @GET("accounts/phone/is/register")
    fun isRegister(@Query("mobi") mobile: String): Call<BaseCniaoRsp>

    @POST("accounts/course/10301/login")
    fun login(@Body reqBody: LoginReqBody): Call<BaseCniaoRsp>

}