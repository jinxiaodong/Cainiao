package com.jarvis.course.utils

import com.jarvis.course.net.CourseListRsp

/**
 * @author jinxiaodong
 * @description：
 * @date 2021/9/9
 */
object CourseUtils {

    @JvmStatic
    fun parseStudentComment(info: CourseListRsp.Data?): String {

        return "${info?.lessons_played_time} ${info?.comment_count}人评价"
    }

    @JvmStatic
    fun parseFree(info: CourseListRsp.Data?): String {
        return if (info?.is_free == 1) "免费" else "￥${info?.now_price}"
    }

}