package com.jarvis.common.net.support

import androidx.lifecycle.LiveData
import com.jarvis.common.net.model.ApiResponse
import com.jarvis.common.net.model.UNKNOWN_ERROR_CODE
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

/**
 * @author jinxiaodong
 * @description：用于将retrofit的call回调数据转化为liveData的adapter
 * @date 2021/8/12
 */
class LiveDataCallAdapter<T>(private val responseType: Type) :
    CallAdapter<T, LiveData<ApiResponse<T>>> {
    override fun responseType() = responseType

    override fun adapt(call: Call<T>): LiveData<ApiResponse<T>> {
        return object : LiveData<ApiResponse<T>>() {

            private var started = AtomicBoolean(false)

            override fun onActive() {
                super.onActive()
                if (started.compareAndSet(false, true)) {
                    call.enqueue(object : Callback<T> {

                        override fun onResponse(call: Call<T>, response: Response<T>) {
                            postValue(ApiResponse.Companion.create(response))
                        }

                        override fun onFailure(call: Call<T>, t: Throwable) {
                            postValue(ApiResponse.Companion.create(UNKNOWN_ERROR_CODE, t))
                        }

                    })
                }
            }
        }
    }


}