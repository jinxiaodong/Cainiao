package com.jarvis.study.repo

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.jarvis.common.model.SingleLiveData
import com.jarvis.study.net.*
import kotlinx.coroutines.flow.Flow

/**
 * @author jinxiaodong
 * @description：
 * @date 2021/9/8
 */
interface IStudyResource {

    val liveStudyInfo: LiveData<StudyInfoRsp>



    /**
     * 学习情况
     */
    suspend fun getStudyInfo()

    /**
     * 最近学习列表
     */
    suspend fun getStudyList(): Flow<PagingData<StudiedRsp.Data>>

    /**
     * 购买的课程
     */
    suspend fun getBoughtCourse(): Flow<PagingData<BoughtRsp.Data>>



    val livePermissionResult: LiveData<HasCoursePermission>
    val liveChapterList: LiveData<ChapterListRsp>
    val livePlayCourse: SingleLiveData<PlayCourseRsp>

    /**
     * 根据课程id查询该用户是否有权限看课程
     */
    suspend fun hasPermission(courseId: Int)

    /**
     * 根据课程id，获取课程的章节课时
     */
    suspend fun getChapters(courseId: Int)

    /**
     * 根据章节课时里面的key，获取对应的视频播放信息，用于播放
     */
    suspend fun getPlayInfo(key: String)
}