package com.jarvis.common.net

import androidx.collection.SimpleArrayMap
import com.google.gson.Gson
import com.jarvis.common.net.support.IHttpCallback
import com.jarvis.common.net.support.OkHttpClientFactory
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * @author jinxiaodong
 * @description：
 * @date 1/11/21
 */
class OkHttpApi private constructor() : HttpApi {

    private val TAG = "OkHttpApi"


    private var baseUrl = "http://api.qingyunke.com/"

    //保存请求，用于取消
    private val callMap = SimpleArrayMap<Any, Call>()

    private val defaultClient = OkHttpClientFactory.getDefaultOkHttpClient()

    private var mClient = defaultClient

    fun getClient() = mClient


    fun initConfig(client: OkHttpClient) {
        mClient = client
    }

    companion object {
        @Volatile
        private var api: OkHttpApi? = null

        fun getInstance(): OkHttpApi {
            return api ?: OkHttpApi().also { api = it }
        }
    }


    override fun get(params: Map<String, Any>, path: String, callback: IHttpCallback) {

        val url = "$baseUrl$path"
        val urlBuilder = url.toHttpUrl().newBuilder()
        params.forEach {
            urlBuilder.addEncodedQueryParameter(it.key, it.value.toString())
        }

        val request = Request.Builder()
            .get()
            .tag(params)
            .url(urlBuilder.build())
            .cacheControl(CacheControl.FORCE_CACHE)
            .build()

        val newCall = mClient.newCall(request)
        callMap.put(request.tag(), newCall)
        newCall.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onFailed(e.message)
            }

            override fun onResponse(call: Call, response: Response) {
                callback.onSuccess(response.body?.toString())
            }

        })


    }

    override fun post(body: Any, path: String, callback: IHttpCallback) {

        val url = "$baseUrl$path"

        val request = Request.Builder()
            .post(Gson().toJson(body).toRequestBody())
            .url(url)
            .tag(body)
            .build()

        val newCall = mClient.newCall(request)
        callMap.put(request.tag(), newCall)
        newCall.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onFailed(e.message)
            }

            override fun onResponse(call: Call, response: Response) {
                callback.onSuccess(response.body?.toString())
            }

        })

    }

    /**
     * 取消网络请求，tag就是每次请求的id 标记，也就是请求的传参
     */
    override fun cancelRequest(tag: Any) {
        callMap[tag]?.cancel()

    }

    /**
     * 取消所有网络请求
     */
    override fun cancleAllRequest() {
        for (i in 0 until callMap.size()) {
            callMap[callMap.keyAt(i)]?.cancel()
        }
    }


    //使用协程形式的 get请求，使用runBlocking，也可以使用suspend修饰

    fun get(params: Map<String, Any>, url: String) = runBlocking {
        val urlBuilder = url.toHttpUrl().newBuilder()

        params.forEach {
            urlBuilder.addEncodedQueryParameter(it.key, it.value.toString())
        }
        val request = Request.Builder()
            .get()
            .tag(params)
            .url(urlBuilder.build())
            .cacheControl(CacheControl.FORCE_NETWORK)
            .build()

        val newCall = mClient.newCall(request)
        callMap.put(request.tag(), newCall)
        newCall.call()
    }


    /**
     * 自定义扩展函数，扩展okHttp的Call的异步执行方式，结合coroutines，返回DataResult的数据响应
     * 也可以使用resumeWith返回的是Result<T>
     * [async] 默认是异步调用，可选参数，false的话就是同步调用
     */
    private suspend fun Call.call(async: Boolean = true): Response {

        return suspendCancellableCoroutine { continuation ->
            if (async) {
                //异步
                enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {

                        if (continuation.isCancelled) return
                        continuation.resumeWithException(e)
                    }

                    override fun onResponse(call: Call, response: Response) {
                        continuation.resume(response)
                    }
                })
            } else {
                //同步
                continuation.resume(execute())
            }
            continuation.invokeOnCancellation {
                try {
                    cancel()
                } catch (e: Exception) {
                }
            }
        }

    }


}
