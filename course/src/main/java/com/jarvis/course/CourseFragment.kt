package com.jarvis.course

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import com.jarvis.common.base.BaseFragment
import com.jarvis.course.databinding.FragmentCourseBinding
import com.jarvis.course.ui.CourseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author jinxiaodong
 * @description：
 * @date 2021/8/25
 */
class CourseFragment : BaseFragment() {

    private val viewModel: CourseViewModel by viewModel()


    override fun getLayoutRes() = R.layout.fragment_course

    override fun bindView(view: View, savedInstanceState: Bundle?): ViewDataBinding {
        return FragmentCourseBinding.bind(view).apply {
            vm = viewModel
        }
    }


    override fun initData() {
        super.initData()
        viewModel.getCourseTypeList()
        //获取到课程类型下的数据列表
        viewModel.apply {
            lifecycleScope.launchWhenCreated {
                typedCourseList().observerKt {data ->
                    data?.let {
                        adapter.submitData(lifecycle, data)
                    }
                }
            }
        }
    }
}