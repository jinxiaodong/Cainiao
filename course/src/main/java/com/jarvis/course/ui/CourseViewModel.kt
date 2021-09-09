package com.jarvis.course.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.jarvis.common.base.BaseViewModel
import com.jarvis.course.net.CourseCategoryRsp
import com.jarvis.course.repo.ICourseResource

/**
 * @author jinxiaodong
 * @description：
 * @date 2021/9/9
 */
class CourseViewModel(private val repo: ICourseResource) : BaseViewModel() {

    val liveStudyInfo: LiveData<CourseCategoryRsp> = repo.liveCourseType


    val adapter = CourseAdapter()

    fun getCourseTypeList() = serverAwait {
        repo.getCourseTypeList()
    }

    suspend fun typedCourseList() = repo.getTypeCourseList()
        .asLiveData(viewModelScope.coroutineContext)
        .cachedIn(viewModelScope)
}