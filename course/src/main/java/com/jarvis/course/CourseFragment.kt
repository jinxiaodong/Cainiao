package com.jarvis.course

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.databinding.ObservableInt
import com.google.android.material.tabs.TabLayout
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.jarvis.common.base.BaseFragment
import com.jarvis.course.databinding.FragmentCourseBinding
import com.jarvis.course.databinding.PopCourseTypeBinding
import com.jarvis.course.ui.CourseAdapter
import com.jarvis.course.ui.CourseLoadAdapter
import com.jarvis.course.ui.CourseViewModel
import kotlinx.android.synthetic.main.fragment_course.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author jinxiaodong
 * @description：
 * @date 2021/8/25
 */
class CourseFragment : BaseFragment() {

    private val viewModel: CourseViewModel by viewModel()

    private val adapter = CourseAdapter()

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

            liveTypes.observerKt { types ->
                tl_category_course.removeAllTabs()
                tl_category_course.addTab(
                    tl_category_course.newTab().also { tab ->
                        tab.text = "全部"
                    }
                )

                types?.forEach {
                    tl_category_course.addTab(
                        tl_category_course.newTab().also { tab ->
                            tab.text = it.title
                        }
                    )
                }
            }

            tl_category_course.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    val types = liveTypes.value ?: return
                    tl_category_course.tabCount
                    val index = tab?.position ?: 0
                    val selCode = if (index > 0) types[index - 1].code ?: "all" else "all"
                    lifecycleScope.launchWhenCreated {
                        typedCourseList(code = selCode).observerKt { data ->
                            data?.let {
                                adapter.submitData(lifecycle, data)
                            }
                        }
                    }
                    LogUtils.i("tab个数 ${tl_category_course.tabCount} types size ${types.size}")
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}

                override fun onTabReselected(tab: TabLayout.Tab?) {}

            })

            rv_course.adapter = adapter.withLoadStateFooter(CourseLoadAdapter(adapter))


            adapter.addLoadStateListener { loadState ->
                if (loadState.refresh is LoadState.Loading) {
                    pb_fragment_course.visibility = View.VISIBLE
                } else {
                    pb_fragment_course.visibility = View.GONE

                    val error = when {
                        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                        else -> null
                    }

                    error?.let {
                        ToastUtils.showShort(it.error.message)
                    }
                }
            }
        }

        //筛选点击
        popFilter()
    }

    private lateinit var popWindow: PopupWindow
    private fun popFilter() {
        val popBinding =
            PopCourseTypeBinding.inflate(LayoutInflater.from(requireContext()), null, false)
        popWindow = PopupWindow(
            popBinding.root,
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true
        )
        val obCheckPos = ObservableInt(0)
        popBinding.apply {
            pos = obCheckPos
            tvAllType.setOnClickListener {
                obCheckPos.set(0)
                popWindow.dismiss()
                adapter.refresh()
            }
            tvBizType.setOnClickListener {
                obCheckPos.set(1)
                popWindow.dismiss()
                adapter.refresh()
            }
            tvProType.setOnClickListener {
                obCheckPos.set(2)
                popWindow.dismiss()
                adapter.refresh()
            }
        }
        //点击筛选
        spinner_type_course.setOnClickListener { v ->
            popWindow.showAsDropDown(v)
        }

    }
}