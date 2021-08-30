package com.jarvis.login.net

import androidx.annotation.Keep

/**
 * @author jinxiaodong
 * @description：登录模块相关的结果响应类
 * @date 2021/8/30
 */

/**
 * 查询手机号码是否注册的接口响应
 */
@Keep
data class RegisterRsp(
    val is_register: Int = FLAG_UN_REGISTERED// 1 表示注册，0 表示未注册
) {
    companion object {
        const val FLAG_IS_REGISTERED = 1//已经注册的
        const val FLAG_UN_REGISTERED = 0//0 表示未注册
    }
}

/**
 * 手机号和密码登录 接口响应
 */
@Keep
data class LoginRsp(
    val course_permission: Boolean,
    val token: String?,
    val user: User?
) {
    @Keep
    data class User(
        val id: Int,//用户id
        val logo_url: String?,//用户头像
        val reg_time: String?,//用户注册时间
        val username: String?//用户名
    )
}