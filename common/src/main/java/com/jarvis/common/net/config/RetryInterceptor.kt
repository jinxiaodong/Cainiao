package com.jarvis.common.net.config

import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author jinxiaodong
 * @description：TODO
 * @date 1/11/21
 * 重试请求拦截器
 */
class RetryInterceptor(private val maxRetry: Int = 0) : Interceptor {

    private var retryTimes = 0


    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        var response = chain.proceed(request)
        while (!response.isSuccessful && retryTimes < maxRetry) {
            retryTimes++
            response = chain.proceed(request)
        }
        return response
    }
}