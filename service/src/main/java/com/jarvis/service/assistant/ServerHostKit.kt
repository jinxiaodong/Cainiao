package com.jarvis.service.assistant

import android.content.Context
import com.didichuxing.doraemonkit.kit.AbstractKit
import com.jarvis.service.R

/**
 * @author jinxiaodong
 * @description：用于配置切换不同的接口host，调试工具
 * @date 2021/8/25
 */
class ServerHostKit : AbstractKit() {

    override val icon = R.drawable.icon_server_host

    override val name = R.string.str_server_host_dokit


    override fun onAppInit(context: Context?) {
        //
    }

    override fun onClick(context: Context?) {
        //
    }
}