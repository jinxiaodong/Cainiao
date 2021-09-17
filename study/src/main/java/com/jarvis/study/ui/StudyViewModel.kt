package com.jarvis.study.ui

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.jarvis.common.base.BaseViewModel
import com.jarvis.service.repo.CniaoUserInfo
import com.jarvis.study.net.StudyInfoRsp
import com.jarvis.study.repo.IStudyResource

/**
 * @author jinxiaodong
 * @description：
 * @date 2021/9/8
 */
class StudyViewModel(private val repo: IStudyResource) : BaseViewModel() {

    //学习页面的数据
    val liveStudyInfo: LiveData<StudyInfoRsp> = repo.liveStudyInfo

    //用户基本信息，头像和名字
    val obUserInfo = ObservableField<CniaoUserInfo>()



    fun getStudyData() = serverAwait {
        repo.getStudyInfo()
    }

    suspend fun studiedList() =
        repo.getStudyList().asLiveData(viewModelScope.coroutineContext).cachedIn(viewModelScope)

    suspend fun boughtList() = repo.getBoughtCourse().asLiveData().cachedIn(viewModelScope)



}