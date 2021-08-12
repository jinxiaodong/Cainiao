package com.jarvis.common.net

import com.jarvis.common.net.support.LiveDataCallAdapterFactory
import com.jarvis.common.net.support.OkHttpClientFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author jinxiaodong
 * @description：Retrofit 请求类
 * @date 2021/8/12
 */
object RetrofitManager {


    private val mOkHttpClient = OkHttpClientFactory.getDefaultOkHttpClient()


    private val retrofitBuilder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(LiveDataCallAdapterFactory())
        .client(mOkHttpClient)


    private var retrofit: Retrofit? = null


    /**
     * 初始化配置
     * [baseUrl]项目接口的基类url，以/结尾
     */

    fun initConfig(baseUrl: String, okClient: OkHttpClient = mOkHttpClient): RetrofitManager {
        retrofit = retrofitBuilder.baseUrl(baseUrl).client(okClient).build()
        return this

    }


    /**
     * 获取retrofit的Service对象
     * [serviceClazz] 定义的retrofit的service 接口类
     */
    fun <T> getService(serviceClazz: Class<T>): T {
        if (retrofit == null) {
            throw UninitializedPropertyAccessException("Retrofit必须初始化，需要配置baseURL")
        } else {
            return this.retrofit!!.create(serviceClazz)
        }
    }

}