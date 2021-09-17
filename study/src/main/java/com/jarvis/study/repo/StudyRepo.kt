package com.jarvis.study.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.blankj.utilcode.util.LogUtils
import com.jarvis.common.model.SingleLiveData
import com.jarvis.common.net.support.serverData
import com.jarvis.service.network.onBizError
import com.jarvis.service.network.onBizOK
import com.jarvis.service.network.onFailure
import com.jarvis.service.network.onSuccess
import com.jarvis.study.net.*
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


    private val _livePermission = MutableLiveData<HasCoursePermission>()
    private val _liveChapterList = MutableLiveData<ChapterListRsp>()
    private val _livePlayCourse = SingleLiveData<PlayCourseRsp>()


    override val livePermissionResult: LiveData<HasCoursePermission> = _livePermission
    override val liveChapterList: LiveData<ChapterListRsp> = _liveChapterList
    override val livePlayCourse: SingleLiveData<PlayCourseRsp> = _livePlayCourse

    override suspend fun hasPermission(courseId: Int) {
        service.getCoursePermission(courseId)
            .serverData()
            .onSuccess {
                //只要不是接口响应成功，
                onBizError { code, message ->
                    LogUtils.w("学习权限 BizError $code,$message")
                }
                onBizOK<HasCoursePermission> { code, data, message ->
                    _livePermission.value = data
                    LogUtils.i("学习权限 BizOK $data")
                    return@onBizOK
                }
            }.onFailure {
                LogUtils.e("学习权限 接口异常 ${it.message}")
            }
    }

    override suspend fun getChapters(courseId: Int) {
        service.getCourseChapter(courseId)
            .serverData()
            .onSuccess {
                //只要不是接口响应成功，
                onBizError { code, message ->
                    LogUtils.w("课时章节 BizError $code,$message")
                }
                onBizOK<ChapterListRsp> { code, data, message ->
                    _liveChapterList.value = data
                    LogUtils.i("课时章节 BizOK $data")
                    return@onBizOK
                }
            }.onFailure {
                LogUtils.e("课时章节 接口异常 ${it.message}")
            }
    }

    override suspend fun getPlayInfo(key: String) {
        service.getCoursePlayUrl(key)
            .serverData()
            .onSuccess {
                //只要不是接口响应成功，
                onBizError { code, message ->
                    LogUtils.w("课时播放信息 BizError $code,$message")
                }
                onBizOK<PlayCourseRsp> { code, data, message ->
                    _livePlayCourse.value = data
                    LogUtils.i("课时播放信息 BizOK $data")
                    return@onBizOK
                }
            }.onFailure {
                LogUtils.e("课时播放信息 接口异常 ${it.message}")
            }
    }
}