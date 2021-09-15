package com.jarvis.home.ui

import com.jarvis.common.base.BaseViewModel
import com.jarvis.home.repo.IHomeResource

/**
 * @author jinxiaodong
 * @description：
 * @date 2021/9/15
 */
class HomeViewModel(private val repo: IHomeResource) : BaseViewModel() {

    val livePageModules = repo.livePageModules

    val liveBanner = repo.liveBanner



    fun getModules() = serverAwait { repo.getModuleList() }


    suspend fun getItems(moduleId: Int) = repo.getModuleItems(moduleId)


    fun getBanner() = serverAwait { repo.getBanner() }
}