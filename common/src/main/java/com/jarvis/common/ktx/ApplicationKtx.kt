package com.jarvis.common.ktx

import android.app.Application

/**
 * @author jinxiaodong
 * @description：Application相关的ktx扩展
 * @date 2021/8/25
 */

val Application.application: Application
    get() = this