package com.jarvis.common.net.config

import androidx.core.net.toUri
import com.jarvis.common.utils.getBaseHost
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author jinxiaodong
 * @description： 用于调试开发期间，替换baseHost的拦截器
 * @date 2021/9/6
 */
class HostInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originRequest = chain.request()
        val oldHost = originRequest.url.host//release.course.api.cniao5.com 不带scheme的
        val oldUrlStr = originRequest.url.toString()
        //调试使用
        val newHost = getBaseHost().toUri().host ?: oldHost
        val newUrlStr = if (newHost == oldHost) {
            oldUrlStr
        } else oldUrlStr.replace(oldHost, newHost)

        val newBuilder = originRequest.newBuilder()
//        LogUtils.i("Host 拦截器 ${originRequest.url} 拦截后 $newUrlStr")
        newBuilder.url(newUrlStr)
        return chain.proceed(newBuilder.build())
    }

}