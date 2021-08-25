package com.jarvis.cainiao

import com.jarvis.common.BaseApplication
import com.jarvis.common.ktx.application
import com.jarvis.service.assistant.AssistantApp

/**
 * @author jinxiaodong
 * @description：
 * @date 2021/8/25
 */
class MyApplication : BaseApplication() {


    override fun initConfig() {
        super.initConfig()

        //doKit的初始化配置
        AssistantApp.initConfig(application)
    }
}