package com.jarvis.common.net.model

/**
 * @author jinxiaodong
 * @description： 基础的网络返回数据结构
 * @date 2021/8/12
 */
data class NetResponse(
    val code: Int,//响应码
    val data: Any?,//响应数据内容
    val message: String//响应数据的结果描述
)