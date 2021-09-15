package com.jarvis.home.repo

import androidx.lifecycle.LiveData
import com.jarvis.common.net.model.DataResult
import com.jarvis.home.net.BannerListRsp
import com.jarvis.home.net.PageModuleListRsp
import com.jarvis.service.network.BaseCniaoRsp

/**
 * @author jinxiaodong
 * @description：
 * @date 2021/9/15
 */
interface IHomeResource {

    val livePageModules: LiveData<PageModuleListRsp>

    val liveBanner: LiveData<BannerListRsp>


    /**
     * 页面模块
     */
    suspend fun getModuleList()


    /**
     * 页面内模块的item列表
     */
    suspend fun getModuleItems(moduleId: Int): DataResult<BaseCniaoRsp>


    /**
     * 获取banner数据
     */
    suspend fun getBanner()


}