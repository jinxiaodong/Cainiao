package com.jarvis.common.webview

import android.webkit.JavascriptInterface
import com.blankj.utilcode.util.LogUtils
import com.jarvis.common.net.config.SP_KEY_USER_TOKEN
import com.jarvis.common.utils.CniaoSpUtils

/**
 * @author jinxiaodong
 * @description：
 * @date 2021/9/17
 */
object JsAndroidApi {

    const val JS_CALL_APP_KEY = "cniaoApp"

    @JavascriptInterface
    fun getAppToken(): String {
        LogUtils.w("JsAndroidApi 中 js调用了getToken")
        return CniaoSpUtils.getString(SP_KEY_USER_TOKEN) ?: ""
    }
}