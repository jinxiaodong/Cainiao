package com.jarvis.study

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.asFlow
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.blankj.utilcode.util.LogUtils
import com.jarvis.common.base.BaseFragment
import com.jarvis.service.repo.CniaoDbHelper
import com.jarvis.study.databinding.FragmentStudyBinding
import com.jarvis.study.ui.StudyLoadStateAdapter
import com.jarvis.study.ui.StudyViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author jinxiaodong
 * @description：
 * @date 2021/8/25
 */
class StudyFragment : BaseFragment() {

    private val viewModel: StudyViewModel by viewModel()

    override fun getLayoutRes() = R.layout.fragment_study

    override fun bindView(view: View, savedInstanceState: Bundle?): ViewDataBinding {
        return FragmentStudyBinding.bind(view).apply {
            vm = viewModel
        }
    }


    override fun initData() {
        super.initData()
        CniaoDbHelper.getLiveUserInfo(requireContext())
            .observerKt {
                viewModel.obUserInfo.set(it)
            }

        viewModel.getStudyData()

        viewModel.apply {
            adapter.withLoadStateFooter(footer = StudyLoadStateAdapter())
            adapter.addLoadStateListener {
                when (it.refresh) {
                    is LoadState.NotLoading -> {

                    }

                    is LoadState.Loading -> {

                    }

                    is LoadState.Error -> {

                    }
                }
                LogUtils.i("loadState $it")
            }

            lifecycleScope.launchWhenStarted {
                studiedList().asFlow().collectLatest { adapter.submitData(lifecycle, it) }
            }
        }
    }

}