package com.jarvis.study

import com.jarvis.common.net.RetrofitManager
import com.jarvis.common.utils.getBaseHost
import com.jarvis.study.net.StudyService
import com.jarvis.study.repo.IStudyResource
import com.jarvis.study.repo.StudyRepo
import com.jarvis.study.ui.StudyViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * @author jinxiaodong
 * @description：
 * @date 2021/9/8
 */
val moduleStudy = module {

    //service retrofit
    single {
        get<RetrofitManager> { parametersOf(getBaseHost()) }.getService(StudyService::class.java)
    }

    //repo LoginResource

    single { StudyRepo(get<StudyService>()) } bind IStudyResource::class
//
    viewModel { StudyViewModel(get()) }
}