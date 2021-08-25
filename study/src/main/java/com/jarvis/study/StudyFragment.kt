package com.jarvis.study

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.jarvis.common.base.BaseFragment
import com.jarvis.study.databinding.FragmentStudyBinding

/**
 * @author jinxiaodong
 * @description：
 * @date 2021/8/25
 */
class StudyFragment : BaseFragment() {
    override fun getLayoutRes(): Int {
        return R.layout.fragment_study
    }

    override fun bindView(view: View, savedInstanceState: Bundle?): ViewDataBinding {
        return FragmentStudyBinding.bind(view)
    }
}