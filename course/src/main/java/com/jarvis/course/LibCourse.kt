package com.jarvis.course.utils

import com.jarvis.common.net.RetrofitManager
import com.jarvis.common.utils.getBaseHost
import com.jarvis.course.net.CourseService
import com.jarvis.course.repo.CourseRepo
import com.jarvis.course.repo.ICourseResource
import com.jarvis.course.ui.CourseViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * @author jinxiaodong
 * @description：
 * @date 2021/9/9
 */

val moduleCourse = module {

    single {
        get<RetrofitManager> { parametersOf(getBaseHost()) }.getService(CourseService::class.java)
    }


    //repo LoginResource

    single { CourseRepo(get()) } bind ICourseResource::class

    viewModel { CourseViewModel(get()) }
}