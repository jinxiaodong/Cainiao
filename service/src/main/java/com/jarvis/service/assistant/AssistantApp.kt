package com.jarvis.service.assistant

import android.app.Application
import com.didichuxing.doraemonkit.DoraemonKit

/**
 * @author jinxiaodong
 * @description：配置doKit的工具类
 * @date 2021/8/25
 */
object AssistantApp {

    fun initConfig(application: Application) {
        DoraemonKit.install(application, mutableListOf(ServerHostKit()))
    }
}