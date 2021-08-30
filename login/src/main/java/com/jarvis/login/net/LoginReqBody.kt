package com.jarvis.login.net

import androidx.annotation.Keep

/**
 * @author jinxiaodong
 * @description：
 * @date 2021/8/27
 */
@Keep
data class LoginReqBody(
    val mobi: String,
    val password: String
)


