package com.jarvis.mine

import com.jarvis.common.net.RetrofitManager
import com.jarvis.common.utils.getBaseHost
import com.jarvis.mine.net.MineService
import com.jarvis.mine.repo.IMineResource
import com.jarvis.mine.repo.MineRepo
import com.jarvis.mine.ui.MineViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * @author jinxiaodong
 * @description：koin的 mine module
 * @date 2021/9/3
 */

val moduleMine = module {

    single {
        get<RetrofitManager> { parametersOf(getBaseHost()) }.getService(MineService::class.java)
    }

    single { MineRepo(get()) } bind IMineResource::class

    viewModel { MineViewModel(get()) }
}