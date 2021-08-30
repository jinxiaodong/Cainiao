package com.jarvis.common.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * @author jinxiaodong
 * @description：viewModel的公共基类
 * @date 2021/8/30
 */
abstract class BaseViewModel : ViewModel() {

    private val jobs = mutableListOf<Job>()
    val isLoading = MutableLiveData<Boolean>()//标记网络loading状态


    /**
     * 协程 网络请求
     */
    fun serverAwait(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch {
            isLoading.value = true
            block.invoke(this)
            isLoading.value = false
        }.addTo(jobs)
    }

    private fun Job.addTo(list: MutableList<Job>) {
        list.add(this)
    }
}