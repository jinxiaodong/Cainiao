package com.jarvis.common

import android.app.Application
import com.blankj.utilcode.util.LogUtils
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * @author jinxiaodong
 * @description：
 * @date 2021/8/25
 */
abstract class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.ERROR)//目前已知bug，除了level.error外，使用androidlogger会导致崩溃
            //context
            androidContext(this@BaseApplication)
            //依赖注入模块
//            modules()
        }
        initConfig()
        initData()

        LogUtils.d("BaseApplication onCreate")
    }

    /**
     * 可用于必要的配置初始化
     */
    protected open fun initConfig() {}

    /**
     * 可用于子类实现必要的数据初始化
     */
    protected open fun initData() {}
}