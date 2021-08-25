package com.jarvis.common.model

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

/**
 * @author jinxiaodong
 * @description：
 * @date 2021/8/24
 */
class SingleLiveData<T> : MutableLiveData<T>() {

    companion object {
        private const val TAG = "SingleLiveData"
    }


    private val mPending = AtomicBoolean(false)


    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
//        super.observe(owner, observer)
        if (hasActiveObservers()) {
            Log.w(TAG, "多个观察者存在的时候，只会有一个被通知到数据更新")
        }

        super.observe(owner, Observer {
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(it)
            }
        })
    }



    override fun setValue(value: T?) {
        mPending.set(true)
        super.setValue(value)
    }


    @MainThread
    fun call() {
        value = null
    }


}