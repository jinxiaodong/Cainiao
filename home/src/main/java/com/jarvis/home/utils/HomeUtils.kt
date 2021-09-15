package com.jarvis.home.utils

import com.jarvis.home.net.HomeCourseItem
import com.jarvis.home.net.PopTeacherList

/**
 * @author jinxiaodong
 * @description：
 * @date 2021/9/15
 */
object HomeUtils {

    @JvmStatic
    fun parseStudentComment(info: HomeCourseItem?): String {
        return "${info?.lessons_played_time} ${info?.comment_count}人评价"
    }

    @JvmStatic
    fun parseFree(info: HomeCourseItem?): String {
        return if (info?.is_free == 1) "免费" else "￥${info?.now_price}"
    }


    @JvmStatic
    fun safeListUrl(info: PopTeacherList.PopTeacherListItem?): String {
        return if (info?.teacher_course.isNullOrEmpty()) "" else info?.teacher_course?.get(0)?.img_url
            ?: ""
    }

    @JvmStatic
    fun safeListTitle(info: PopTeacherList.PopTeacherListItem?): String {
        return if (info?.teacher_course.isNullOrEmpty()) "" else info?.teacher_course?.get(0)?.name
            ?: ""
    }

    @JvmStatic
    fun safeListComment(info: PopTeacherList.PopTeacherListItem?): String {
        return if (info?.teacher_course.isNullOrEmpty()) "" else "${info?.teacher_course?.get(0)?.lessons_played_time} ${
            info?.teacher_course?.get(
                0
            )?.comment_count
        }人评价"
    }

    @JvmStatic
    fun safeListPrice(info: PopTeacherList.PopTeacherListItem?): String {
        return if (info?.teacher_course.isNullOrEmpty()) "" else "${info?.teacher_course?.get(0)?.now_price}"
    }

}