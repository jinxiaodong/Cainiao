package com.jarvis.service

import com.jarvis.common.net.RetrofitManager
import org.koin.dsl.module

/**
 * @author jinxiaodong
 * @description：Service模块相关的koin的module配置
 * @date 2021/8/25
 */
val moduleService = module {

    single<RetrofitManager> { (host: String) -> RetrofitManager.initConfig(host) }
}