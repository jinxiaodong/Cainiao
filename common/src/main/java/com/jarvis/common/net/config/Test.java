package com.jarvis.common.net.config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author jinxiaodong
 * @description：
 * @date 2021/8/12
 */
public class Test {
    /**
     * unicode 转中文
     *
     * @param string
     * @return
     */
    public static String unicodeDecode(String string) {
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(string);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
//            Integer.valueOf("", 16);
            string = string.replace(matcher.group(1), ch + "");
        }
        return string;
    }


}
