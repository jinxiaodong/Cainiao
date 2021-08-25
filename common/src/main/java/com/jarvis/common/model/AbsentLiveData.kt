package com.jarvis.common.model

import androidx.lifecycle.LiveData

/**
 * @author jinxiaodong
 * @description：
 * @date 2021/8/24
 */
class AbsentLiveData<T : Any?> private constructor() : LiveData<T>() {

    init {
        postValue(null)
    }

    companion object {
        fun <T : Any?> create(): LiveData<T> {
            return AbsentLiveData()
        }
    }
}