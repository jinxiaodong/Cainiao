package com.jarvis.common.net.config

import android.util.Log
import com.jarvis.common.net.support.CniaoUtils
import okhttp3.Connection
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.net.URLDecoder
import java.text.SimpleDateFormat
import java.util.*
import kotlin.text.StringBuilder

/**
 * @author jinxiaodong
 * @description：
 * @date 1/11/21
 */
class HttpLogInterceptor(block: (HttpLogInterceptor.() -> Unit)? = null) : Interceptor {


    private var logLevel = LogLevel.NONE  //打印日志标记
    private var colorLevel = ColorLevel.DEBUG  //打印日志标记
    private var logTag = TAG //打印日志标记


    init {
        block?.invoke(this)
    }

    fun logLevel(level: LogLevel): HttpLogInterceptor {
        logLevel = level
        return this
    }

    fun colorLevel(level: ColorLevel): HttpLogInterceptor {
        colorLevel = level
        return this

    }

    fun logTag(tag: String): HttpLogInterceptor {
        logTag = tag
        return this

    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        return kotlin.runCatching {
            chain.proceed(request)
        }.onFailure {
            it.printStackTrace()
            logIt(
                it.message.toString(),
                ColorLevel.ERROR
            )
        }.onSuccess { response ->
            if (logLevel == LogLevel.NONE) {
                return response
            }
            logRequest(request, chain.connection())
            logResponse(response)
        }.getOrThrow()
    }


    private fun logRequest(request: Request, connection: Connection?) {
        val sb = StringBuilder()
        sb.appendLine("\r\n")
        sb.appendLine("----------------------------------------------------------------------")
        when (logLevel) {
            LogLevel.NONE -> {
            }
            LogLevel.BASIC -> {
                logBasicReq(sb, request, connection)
            }
            LogLevel.HEADERS -> {
                logHeadersReq(sb, request, connection)

            }
            LogLevel.BODY -> {
                logBodyReq(sb, request, connection)
            }
        }
        sb.appendLine("----------------------------------------------------------------------")
        logIt(sb)
    }

    private fun logBasicReq(sb: StringBuilder, request: Request, connection: Connection?) {
        sb.appendLine("请求 method:${request.method} url:${decodeUrlString(request.url.toString())} tag:")
    }

    private fun logHeadersReq(sb: StringBuilder, request: Request, connection: Connection?) {
        logBasicReq(sb, request, connection)
        val headerString = request.headers.joinToString("") { headers ->
            "请求 Header:{${headers.first} = ${headers.second}}\n"
        }
        sb.appendLine(headerString)
    }

    private fun logBodyReq(sb: StringBuilder, request: Request, connection: Connection?) {
        logHeadersReq(sb, request, connection)
        sb.appendLine("RequestBody:${request.body.toString()}")
    }


    private fun logResponse(response: Response) {
        val sb = java.lang.StringBuilder()
        sb.appendLine("\r\n")
        sb.appendLine("----------------------------------------------------------------------")
        when (logLevel) {
            LogLevel.NONE -> {
            }
            LogLevel.BASIC -> {
                logBasicRsp(sb, response)
            }
            LogLevel.HEADERS -> {
                logHeadersRsp(sb, response)

            }
            LogLevel.BODY -> {
                logHeadersRsp(sb, response)
                kotlin.runCatching {
                    val peekBody = response.peekBody(1024 * 1024)
                    sb.appendLine(CniaoUtils.unicodeDecode(peekBody.string()))
                }.getOrNull()
            }
        }
        sb.appendLine("----------------------------------------------------------------------")
        logIt(sb, ColorLevel.INFO)
    }


    private fun logHeadersRsp(sb: StringBuilder, response: Response) {
        val headerString = response.headers.joinToString(separator = "") { header ->
            "响应 Header:{${header.first}=${header.second}}\n"
        }
        sb.appendLine(headerString)
    }

    private fun logBasicRsp(sb: StringBuilder, response: Response) {
        sb.append("响应 protocol:${response.protocol} code:${response.code} message:${response.message}")
            .appendLine("响应 request Url:${decodeUrlString(response.request.url.toString())}")
            .appendLine(
                "响应 sentRequestTime:${
                    toDateTimeStr(
                        response.sentRequestAtMillis,
                        MILLIS_PATTERN
                    )
                } receivedResponseTime:${
                    toDateTimeStr(
                        response.receivedResponseAtMillis,
                        MILLIS_PATTERN
                    )
                }"
            )
    }


    fun decodeUrlString(url: String): String? {
        return kotlin.runCatching {
            URLDecoder.decode(url, "utf-8")
        }.onFailure {
            it.printStackTrace()
        }.getOrNull()

    }

    private fun logIt(any: Any, tempLevel: ColorLevel? = null) {
        when (tempLevel ?: colorLevel) {
            ColorLevel.VERBOSE -> Log.v(logTag, any.toString())
            ColorLevel.DEBUG -> Log.d(logTag, any.toString())
            ColorLevel.INFO -> Log.i(logTag, any.toString())
            ColorLevel.WARN -> Log.w(logTag, any.toString())
            ColorLevel.ERROR -> Log.e(logTag, any.toString())

        }

    }

    companion object {
        private const val TAG = "<KtHttp>"

        const val MILLIS_PATTERN = "yyyy-MM-dd HH:mm:ss.SSSXXX"


        fun toDateTimeStr(millis: Long, pattern: String): String {
            return SimpleDateFormat(pattern, Locale.getDefault()).format(millis)
        }
    }


    enum class LogLevel {
        NONE,//不答应
        BASIC,//只打印请求/响应首行
        HEADERS,//打印请求和响应的所有Header
        BODY,//打印所有
    }

    enum class ColorLevel {

        VERBOSE,
        DEBUG,
        INFO,
        WARN,
        ERROR,

    }
}