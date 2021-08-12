package com.jarvis.common.net.support

import android.os.Environment
import com.jarvis.common.net.config.CniaoInterceptor
import com.jarvis.common.net.config.HttpLogInterceptor
import com.jarvis.common.net.config.LocalCookieJar
import com.jarvis.common.net.config.RetryInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * @author jinxiaodong
 * @description：
 * @date 2021/8/12
 */
class OkHttpClientFactory {

    companion object {
        var maxRetry = 0
        fun getDefaultOkHttpClient(): OkHttpClient {

            return OkHttpClient.Builder()
                .callTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .followRedirects(false)//重定向
                .cache(Cache(File(Environment.getDataDirectory(), "okhttp/cache"), 1024))
                .cookieJar(LocalCookieJar())
                .addNetworkInterceptor(CniaoInterceptor())
                .addNetworkInterceptor(HttpLogInterceptor {
                    logLevel(HttpLogInterceptor.LogLevel.BODY)
                    colorLevel(HttpLogInterceptor.ColorLevel.INFO)
                    logTag("Jarvis")
                })
                .addNetworkInterceptor(RetryInterceptor(maxRetry))
                .build()
        }
    }
}