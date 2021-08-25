package com.jarvis.course

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.jarvis.common.base.BaseFragment
import com.jarvis.course.databinding.FragmentCourseBinding

/**
 * @author jinxiaodong
 * @description：
 * @date 2021/8/25
 */
class CourseFragment : BaseFragment() {

    override fun getLayoutRes() = R.layout.fragment_course

    override fun bindView(view: View, savedInstanceState: Bundle?): ViewDataBinding {
        return FragmentCourseBinding.bind(view)
    }

}