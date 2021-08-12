package com.jarvis.common.net.config

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

/**
 * @author jinxiaodong
 * @description：
 * @date 1/11/21
 */
class LocalCookieJar : CookieJar {

    //cookie的本地化存储
    private val cache = mutableListOf<Cookie>()

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        //过期的Cookie
        val invalidCookies: MutableList<Cookie> = ArrayList()
        //有效的Cookie
        val validCookies: MutableList<Cookie> = ArrayList()
        cache.forEach {
            if (it.expiresAt < System.currentTimeMillis()) {
                //是否过期
                invalidCookies.add(it)
            } else {
                validCookies.add(it)
            }
        }
        //移除缓存中的cookie
        cache.removeAll(invalidCookies)
        //返回有效cookie
        return validCookies
    }

    /**
     *保存cookie
     */
    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        cache.addAll(cookies)
    }

}