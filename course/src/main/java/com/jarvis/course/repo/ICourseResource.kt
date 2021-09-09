package com.jarvis.course.repo

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.jarvis.course.net.CourseCategoryRsp
import com.jarvis.course.net.CourseListRsp
import kotlinx.coroutines.flow.Flow

/**
 * @author jinxiaodong
 * @description：
 * @date 2021/9/9
 */
interface ICourseResource {

    val liveCourseType: LiveData<CourseCategoryRsp>

    /**
     * 课程分类列表
     *
     */
    suspend fun getCourseTypeList()


    /**
     * 根据类别获取课程列表
     */
    suspend fun getTypeCourseList(): Flow<PagingData<CourseListRsp.Data>>
}