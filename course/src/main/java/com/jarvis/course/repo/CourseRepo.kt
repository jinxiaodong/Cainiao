package com.jarvis.course.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.blankj.utilcode.util.LogUtils
import com.jarvis.common.net.support.serverData
import com.jarvis.course.net.CourseCategoryRsp
import com.jarvis.course.net.CourseListRsp
import com.jarvis.course.net.CourseService
import com.jarvis.course.repo.data.CoursePagingSource
import com.jarvis.service.network.onBizError
import com.jarvis.service.network.onBizOK
import com.jarvis.service.network.onFailure
import com.jarvis.service.network.onSuccess
import kotlinx.coroutines.flow.Flow

/**
 * @author jinxiaodong
 * @description：
 * @date 2021/9/9
 */
class CourseRepo(private val service: CourseService) : ICourseResource {

    private val pageSize = 20

    private val _courseType = MutableLiveData<CourseCategoryRsp>()

    override val liveCourseType: LiveData<CourseCategoryRsp> = _courseType


    override suspend fun getCourseTypeList() {
        service.getCourseCategory()
            .serverData()
            .onSuccess {
                onBizError { code, message ->
                    LogUtils.w("获取课程分类 BizError $code,$message")
                }
                onBizOK<CourseCategoryRsp> { code, data, message ->
                    _courseType.value = data
                    LogUtils.i("获取课程分类 BizOK $data")

                    return@onBizOK
                }
            }
            .onFailure {
                LogUtils.e("获取课程分类 接口异常 ${it.message}")

            }

    }

    override suspend fun getTypeCourseList(
        course_type: Int,
        code: String,
        difficulty: Int,
        is_free: Int,
        q: Int
    ): Flow<PagingData<CourseListRsp.Data>> {
        val config =
            PagingConfig(
                pageSize = pageSize,
                prefetchDistance = 5,
                initialLoadSize = 10,
                maxSize = pageSize * 3
            )

        return Pager(config = config, null) {
            CoursePagingSource(service,course_type, code, difficulty, is_free, q)
        }.flow
    }


}