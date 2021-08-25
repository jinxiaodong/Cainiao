package com.jarvis.common.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.LogUtils
import com.jarvis.common.ktx.bindView
import com.jarvis.common.ktx.viewLifeCycleOwner

/**
 * @author jinxiaodong
 * @description：
 * @date 2021/8/24
 */
abstract class BaseActivity<dataBindType : ViewDataBinding> : AppCompatActivity {


    constructor() : super()

    constructor(layoutId: Int) : super(layoutId)

    protected lateinit var mBinding: dataBindType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = bindView<dataBindType>(getLayoutRes())

        initConfig()
        initView()
        initData()

    }

    @LayoutRes
    abstract fun getLayoutRes(): Int

    /**
     * view初始化后的必要配置
     */
    open fun initConfig() {
        LogUtils.d("${this.javaClass.simpleName} 初始化 initConfig")
    }

    /**
     * view初始化后的必要配置
     */
    open fun initView() {
        LogUtils.d("${this.javaClass.simpleName} 初始化 initConfig")
    }

    /**
     * view初始化后的必要数据
     */
    open fun initData() {
        LogUtils.d("${this.javaClass.simpleName} 初始化 initData")
    }


    override fun onDestroy() {
        super.onDestroy()
        if (this::mBinding.isLateinit) {
            mBinding.unbind()
        }
    }

    /**
     * 扩展liveData的observe函数
     */
    protected fun <T> LiveData<T>.observerKt(block: (T?) -> Unit) {
        this.observe(viewLifeCycleOwner, { data -> block(data) })
    }

}