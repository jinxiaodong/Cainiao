package com.jarvis.home.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.LogUtils
import com.jarvis.common.net.model.DataResult
import com.jarvis.common.net.support.serverData
import com.jarvis.home.net.BannerListRsp
import com.jarvis.home.net.HomeService
import com.jarvis.home.net.PageModuleListRsp
import com.jarvis.service.network.*

/**
 * @author jinxiaodong
 * @description：
 * @date 2021/9/15
 */
class HomeRepo(private val service: HomeService) : IHomeResource {

    private val _livePageModules = MutableLiveData<PageModuleListRsp>()

    private val _liveBanner = MutableLiveData<BannerListRsp>()

    override val livePageModules = _livePageModules

    override val liveBanner = _liveBanner


    override suspend fun getModuleList() {

        service.getPageModuleList()
            .serverData()
            .onSuccess {
                onBizOK<PageModuleListRsp> { code, data, message ->
                    _livePageModules.value = data
                    LogUtils.i("首页的配置 onBizeOK $data")
                }

                onBizError { code, message ->
                    LogUtils.e("首页的配置onBizError $message $code")
                }
            }
            .onFailure {
                LogUtils.e("首页的配置onFailure ${it.message}")

            }
    }

    override suspend fun getModuleItems(moduleId: Int): DataResult<BaseCniaoRsp> {
        return service.getModuleItems(moduleId).serverData()
    }

    override suspend fun getBanner() {
        service.getBannerList()
            .serverData()
            .onSuccess {
                onBizOK<BannerListRsp> { code, data, message ->
                    _liveBanner.value = data
                    LogUtils.i("首页banner onBizOK $data")
                }

                onBizError { code, message ->
                    LogUtils.e("首页banner onBizError $message $code")
                }
            }
            .onFailure {
                LogUtils.e("首页banner onFailure ${it.message}")
            }
    }
}