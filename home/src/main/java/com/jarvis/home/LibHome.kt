package com.jarvis.home

import com.jarvis.common.net.RetrofitManager
import com.jarvis.common.utils.getBaseHost
import com.jarvis.home.net.HomeService
import com.jarvis.home.repo.HomeRepo
import com.jarvis.home.repo.IHomeResource
import com.jarvis.home.ui.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * @author jinxiaodong
 * @description：
 * @date 2021/9/15
 */

val moduleHome = module {

    single {
        get<RetrofitManager> { parametersOf(getBaseHost()) }.getService(HomeService::class.java)
    }

    single { HomeRepo(get()) } bind IHomeResource::class

    viewModel { HomeViewModel(get()) }
}