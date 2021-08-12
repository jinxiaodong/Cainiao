package com.jarvis.common.net.support

/**
 * @author jinxiaodong
 * @description：
 * @date 1/11/21
 * 网络请求接口回调
 */
interface IHttpCallback {

    /**
     * 网络请求成功的回调
     * [data] 返回回调的数据结果
     */
    fun onSuccess(data: Any?)


    /**
     * 接口失败回调
     * [error] 错误信息数据类
     */
    fun onFailed(error: Any?)

}
