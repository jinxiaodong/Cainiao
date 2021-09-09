package com.jarvis.study.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.blankj.utilcode.util.LogUtils
import com.jarvis.common.net.support.serverData
import com.jarvis.service.network.onBizError
import com.jarvis.service.network.onBizOK
import com.jarvis.service.network.onFailure
import com.jarvis.service.network.onSuccess
import com.jarvis.study.net.BoughtRsp
import com.jarvis.study.net.StudiedRsp
import com.jarvis.study.net.StudyInfoRsp
import com.jarvis.study.net.StudyService
import com.jarvis.study.repo.data.BoughtItemPagingSource
import com.jarvis.study.repo.data.StudiedItemPagingSource
import kotlinx.coroutines.flow.Flow

/**
 * @author jinxiaodong
 * @description：
 * @date 2021/9/8
 */
class StudyRepo(private val service: StudyService) : IStudyResource {

    private val _studyInfo = MutableLiveData<StudyInfoRsp>()
    private val pageSize = 10//页码大小

    override val liveStudyInfo: LiveData<StudyInfoRsp> = _studyInfo

    override suspend fun getStudyInfo() {
        service.getStudyInfo()
            .serverData()
            .onSuccess {
                //只要不是接口响应成功，
                onBizError { code, message ->
                    LogUtils.w("获取学习信息 BizError $code,$message")
                }
                onBizOK<StudyInfoRsp> { code, data, message ->
                    _studyInfo.value = data
                    LogUtils.i("获取学习信息 BizOK $data")
                    return@onBizOK
                }
            }.onFailure {
                LogUtils.e("获取学习信息 接口异常 ${it.message}")
            }
    }

    override suspend fun getStudyList(): Flow<PagingData<StudiedRsp.Data>> {
        val config =
            PagingConfig(
                pageSize = pageSize,
                prefetchDistance = 5,
                initialLoadSize = 10,
                maxSize = pageSize * 3
            )
        return Pager(config = config, null) {
            StudiedItemPagingSource(service)
        }.flow
    }

    override suspend fun getBoughtCourse(): Flow<PagingData<BoughtRsp.Data>> {
        val config =
            PagingConfig(
                pageSize = pageSize,
                prefetchDistance = 5,
                initialLoadSize = 10,
                maxSize = pageSize * 3
            )
        return Pager(config = config, null) {
            BoughtItemPagingSource(service)
        }.flow
    }
}