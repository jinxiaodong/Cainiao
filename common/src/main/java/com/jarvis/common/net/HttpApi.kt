package com.jarvis.common.net

import com.jarvis.common.net.support.IHttpCallback

/**
 * @author jinxiaodong
 * @description：TODO
 * @date 1/11/21
 *
 * 网络请求的统一接口类
 */
interface HttpApi {


    /**
     * 抽象的http的get请求封装，异步
     */
    fun get(params: Map<String, Any>, path: String, callback: IHttpCallback)


    /**
     *
    /**
     * 抽象的http的get请求封装，同步
    */
     */
    fun getSync(params: Map<String, Any>, path: String): Any? = Any()

    /**
     * 抽象的http的post的请求
     */
    fun post(body: Any, path: String, callback: IHttpCallback)

    /**
     * 抽象的Http的post同步请求
     */
    fun postSync(body: Any, path: String): Any? = Any()


    fun cancelRequest(tag: Any)

    fun cancleAllRequest()
}