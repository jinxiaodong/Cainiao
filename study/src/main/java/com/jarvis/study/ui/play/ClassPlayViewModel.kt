package com.jarvis.study.ui.play

import com.jarvis.common.base.BaseViewModel
import com.jarvis.study.repo.IStudyResource

/**
 * @author jinxiaodong
 * @description：
 * @date 2021/9/16
 */
class ClassPlayViewModel(private val repo: IStudyResource) : BaseViewModel() {


    val liveCoursePermission = repo.livePermissionResult
    val liveChapterList = repo.liveChapterList
    val livePlayInfo = repo.livePlayCourse


    fun checkPermission(courseId: Int) = serverAwait { repo.hasPermission(courseId) }

    fun getChapters(courseId: Int) = serverAwait { repo.getChapters(courseId) }

    fun getPlayInfo(key: String) = serverAwait { repo.getPlayInfo(key) }

}