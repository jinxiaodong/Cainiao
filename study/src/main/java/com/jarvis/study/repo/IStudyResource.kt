package com.jarvis.study.repo

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.jarvis.study.net.BoughtRsp
import com.jarvis.study.net.StudiedRsp
import com.jarvis.study.net.StudyInfoRsp
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


}