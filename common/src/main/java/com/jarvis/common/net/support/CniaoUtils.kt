@file:JvmName("CniaoUtils")

package com.jarvis.common.net.support

import com.blankj.utilcode.util.EncryptUtils
import com.jarvis.common.net.config.NET_CONFIG_APPKEY
import java.util.regex.Pattern

/**
 * @author jinxiaodong
 * @description：
 * @date 2021/8/11
 */
object CniaoUtils {


    /**
     * 中文转unicode
     * @param str 中文
     * @return  中文对应unicode
     */
    fun unicodeEncode(str: String): String {
        val uftBytes = str.toCharArray()
        var unicodeBytes = str

        uftBytes.forEach {
            Int
            var hexB = Integer.toHexString(it.code)
            if (hexB.length <= 2) {
                hexB = "00$hexB"
            }
            unicodeBytes = "$unicodeBytes\\u$hexB"
        }
        return unicodeBytes
    }


    /**
     * unicode 转中文
     *
     * @param string
     * @return
     */
    fun unicodeDecode(string: String): String? {
        var string = string
        val pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))")
        val matcher = pattern.matcher(string)
        var ch: Char
        while (matcher.find()) {
            ch = matcher.group(2).toInt(16).toChar()
            string = string.replace(matcher.group(1), ch.toString() + "")
        }
        return string
    }

//    /**
//     * unicode 转中文
//     *
//     * @param str unicode
//     * @return 中文
//     */
//    fun unicodeDecode(str: String): String {
//        val pattern = Pattern.compile("(\\\\u(\\P{XDigit}{4}))")
//        val matcher = pattern.matcher(str)
//        var strResult = str
//        var ch: Char? = null
//        while (matcher.find()) {
//            ch = matcher.group(2)?.toInt(16)?.toChar()
//            matcher.group(1)?.apply {
//                strResult.replace(this, ch.toString())
//            }
//        }
//        return strResult
//    }


    /**
     * 解析返回的data数据
     *
     * @param dataStr 未解密的响应数据
     * @return 解密后的数据String
     */
    fun decodeData(dataStr: String?): String? {
        //java代码，无自动null判断，需要自行处理
        return if (dataStr != null) {
            String(
                EncryptUtils.decryptBase64AES(
                    dataStr.toByteArray(), NET_CONFIG_APPKEY.toByteArray(),
                    "AES/CBC/PKCS7Padding",
                    "J#y9sJesv*5HmqLq".toByteArray()
                )
            )
        } else {
            null
        }
    }
}