package com.jarvis.cainiao

import com.alibaba.android.arouter.launcher.ARouter
import com.jarvis.common.BaseApplication
import com.jarvis.common.ktx.application
import com.jarvis.course.utils.moduleCourse
import com.jarvis.home.moduleHome
import com.jarvis.login.moduleLogin
import com.jarvis.mine.moduleMine
import com.jarvis.service.assistant.AssistantApp
import com.jarvis.service.moduleService
import com.jarvis.study.moduleStudy
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module

/**
 * @author jinxiaodong
 * @description：
 * @date 2021/8/25
 */
class MyApplication : BaseApplication() {


    private val modules = arrayListOf<Module>(
        moduleService,
        moduleHome,
        moduleLogin,
        moduleMine,
        moduleCourse,
        moduleStudy
    )

    override fun initConfig() {
        super.initConfig()

        //doKit的初始化配置
        AssistantApp.initConfig(application)
        loadKoinModules(modules)
        ARouter.init(application)
    }
}