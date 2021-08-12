package com.jarvis.common.net.config

import com.blankj.utilcode.util.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import okhttp3.CacheControl
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer

/**
 * @author jinxiaodong
 * @description： 公共header拦截器
 * @date 2021/8/12
 */
class CniaoInterceptor : Interceptor {

    companion object {

        private val gson = GsonBuilder()
            .enableComplexMapKeySerialization()
            .create()

        private val mapType = object : TypeToken<Map<String, Any>>() {}.type
    }


    override fun intercept(chain: Interceptor.Chain): Response {

        val originalRequest = chain.request()

        //附加的公共headers，封装clientInfo,deviceInfo等。也可以在post请求中，自定义封装headers的字段体内容
        //注意这里，服务器用于校验的字段，只能是以下的字段内容，可以缺失，但不能额外添加，因为服务端未做处理

        val attachHeaders = mutableListOf<Pair<String, String>>(
            "appid" to NET_CONFIG_APPID,
            "platform" to "android", //如果重复请求，可能会报重复签名错误，yapi 平台标记则不会
            "timestamp" to System.currentTimeMillis().toString(),

            "brand" to DeviceUtils.getManufacturer(),
            "model" to DeviceUtils.getModel(),
            "uuid" to DeviceUtils.getUniqueDeviceId(),
            "network" to NetworkUtils.getNetworkType().name,
            "system" to DeviceUtils.getSDKVersionName(),

            "version" to AppUtils.getAppVersionName()
        )

        val tokenStr = ""
        val localToken = SPStaticUtils.getString(SP_KEY_USER_TOKEN, tokenStr)

        if (localToken.isNotEmpty()) {
            attachHeaders.add("token" to localToken)
        }

        //请求参数签名计算
        val signHeaders = mutableListOf<Pair<String, String>>()
        signHeaders.addAll(attachHeaders)
        //get的请求参数
        if (originalRequest.method == "GET") {
            originalRequest.url.queryParameterNames.forEach {
                signHeaders.add(it to (originalRequest.url.queryParameter(it) ?: ""))
            }
        }
        //post的请求 formBody形式，或json形式的，都需要将内部的字段，遍历出来，参与sign的计算
        val requestBody = originalRequest.body
        if (originalRequest.method == "POST") {
            //form表单
            if (requestBody is FormBody) {
                for (i in 0 until requestBody.size) {
                    signHeaders.add(requestBody.name(i) to requestBody.value(i))
                }
            }
            //json的body 需要将requestBody反序列化为json转为map application/json
            if (requestBody?.contentType()?.type == "application" && requestBody.contentType()?.subtype == "json") {
                kotlin.runCatching {
                    var buffer = Buffer()
                    requestBody.writeTo(buffer)
                    buffer.readByteString().utf8()
                }.onSuccess {
                    val map = gson.fromJson<Map<String, Any>>(it, mapType)
                    map.forEach { entry ->
                        signHeaders.add(entry.key to entry.value.toString())
                    }
                }
            }
        }

        //todo 算法：都必须是非空参数！！！  sign = MD5（ascii排序后的 headers及params的key=value拼接&后，最后拼接appkey和value）//32位的大写,
        val singValue = signHeaders
            .sortedBy { it.first }
            .joinToString("&") { "${it.first} = ${it.second}" }
            .plus("&appkey=$NET_CONFIG_APPKEY")

        val newBuilder = originalRequest.newBuilder()
            .cacheControl(CacheControl.FORCE_NETWORK)
        attachHeaders.forEach { newBuilder.header(it.first, it.second) }
        LogUtils.i(singValue)
        newBuilder.header("sign", EncryptUtils.encryptMD5ToString(singValue))

        if (originalRequest.method == "POST" && requestBody != null) {
            newBuilder.post(requestBody)
        } else if (originalRequest.method == "GET") {
            newBuilder.get()
        }
        return chain.proceed(newBuilder.build())

    }


}
