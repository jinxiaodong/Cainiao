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
    suspend fun getTypeCourseList(
        course_type: Int,//类型 (-1 全部) (1 普通课程) (2 职业课程/班级课程) (3 实战课程) 默认 -1
        code: String,//方向从课程分类接口获取    默认 all;例如 android,python
        difficulty: Int,//难度 (-1 全部) (1 初级) (2 中级) (3 高级) (4 架构) 默认 -1
        is_free: Int,//价格 (-1, 全部) （0 付费） (1 免费) 默认 -1
        q: Int,//排序  (-1 最新) (1 评价最高)  (2 学习最多) 默认 -1
    ): Flow<PagingData<CourseListRsp.Data>>
}