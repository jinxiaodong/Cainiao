package com.jarvis.login

import com.jarvis.common.net.RetrofitManager
import com.jarvis.common.utils.getBaseHost
import com.jarvis.login.net.LoginService
import com.jarvis.login.repo.ILoginResource
import com.jarvis.login.repo.LoginRepo
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.bind
import org.koin.dsl.module


/**
 * @author jinxiaodong
 * @description：
 * @date 2021/8/30
 */
val moduleLogin = module {

    //service retrofit
    single {
        get<RetrofitManager> { parametersOf(getBaseHost()) }.getService(LoginService::class.java)
    }

    //repo LoginResource

    single { LoginRepo(get()) } bind ILoginResource::class

    //viewModel

    viewModel { LoginViewModel(get()) }

}