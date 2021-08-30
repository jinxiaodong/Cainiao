package com.jarvis.login

import com.jarvis.common.BaseApplication
import com.jarvis.service.moduleService
import org.koin.core.context.loadKoinModules

/**
 * @author jinxiaodong
 * @description：
 * @date 2021/8/30
 */
class LoginApplication : BaseApplication() {

    override fun initConfig() {
        super.initConfig()
        loadKoinModules(moduleService)
        loadKoinModules(moduleLogin)

    }

}