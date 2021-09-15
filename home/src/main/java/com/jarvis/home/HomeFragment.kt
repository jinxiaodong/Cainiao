package com.jarvis.home

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.LogUtils
import com.jarvis.common.base.BaseFragment
import com.jarvis.common.net.model.DataResult
import com.jarvis.home.databinding.FragmentHomeBinding
import com.jarvis.home.net.*
import com.jarvis.home.ui.BannerAdapter
import com.jarvis.home.ui.HomeAdapter
import com.jarvis.home.ui.HomeItem
import com.jarvis.home.ui.HomeViewModel
import com.jarvis.service.network.*
import com.youth.banner.indicator.CircleIndicator
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author jinxiaodong
 * @description：
 * @date 2021/8/25
 */
class HomeFragment : BaseFragment() {

    private val viewModel: HomeViewModel by viewModel()

    private val mBanners = mutableListOf<BannerItem>()

    private val bannerAdapter by lazy { BannerAdapter(mBanners) }


    private val homeAdapter = HomeAdapter()
    private val homeList = mutableListOf<HomeItem>()


    override fun getLayoutRes() = R.layout.fragment_home

    override fun bindView(view: View, savedInstanceState: Bundle?): ViewDataBinding {
        return FragmentHomeBinding.bind(view)
            .apply {
                vm = viewModel
                adapter = homeAdapter
            }
    }

    override fun initConfig() {
        super.initConfig()
        banner_home.addBannerLifecycleObserver(viewLifecycleOwner)
            .setAdapter(bannerAdapter).indicator = CircleIndicator(requireContext())
    }


    override fun initData() {
        super.initData()
        viewModel.apply {
            getBanner()
            getModules()

            liveBanner.observerKt {
                it ?: return@observerKt
                mBanners.clear()
                mBanners.addAll(it)
                bannerAdapter.notifyDataSetChanged()
            }

            val scope = CoroutineScope(SupervisorJob())

            livePageModules.observerKt { modules ->

                lifecycleScope.launchWhenCreated {
                    modules?.map {
                        it.id to scope.async {
                            getItems(it.id)
                        }
                    }?.asFlow()?.collect {
                        parseResult(it.first, it.second.await())
                    }
                    homeAdapter.updateList(homeList)
                }

            }

        }
    }

    private fun parseResult(typeId: Int, data: DataResult<BaseCniaoRsp>) {
        data.onSuccess {
            when (typeId) {
                TYPE_JOB_CLASS -> {
                    onBizOK<JobClassList> { code, data, message ->
                        homeList.add(HomeItem(typeId, "就业班", data))
                    }
                }
                TYPE_NEW_CLASS -> {
                    onBizOK<NewClassList> { code, data, message ->
                        homeList.add(HomeItem(typeId, "新上好课", data))
                    }
                }
                TYPE_LIMIT_FREE -> {
                    onBizOK<LimitFreeList> { code, data, message ->
                        homeList.add(HomeItem(typeId, "限时免费", data))
                    }
                }
                TYPE_ANDROID -> {
                    onBizOK<AndroidSelection> { code, data, message ->
                        homeList.add(HomeItem(typeId, "Android精选", data))
                    }
                }
                TYPE_AI -> {
                    onBizOK<AISelection> { code, data, message ->
                        homeList.add(HomeItem(typeId, "AI精选", data))
                    }
                }
                TYPE_BD -> {
                    onBizOK<BDList> { code, data, message ->
                        homeList.add(HomeItem(typeId, "大数据精选", data))
                    }
                }
                TYPE_10W -> {
                    onBizOK<Suggest10w> { code, data, message ->
                        homeList.add(HomeItem(typeId, "10w学员推荐", data))
                    }
                }
                TYPE_TEACHER -> {
                    onBizOK<PopTeacherList> { code, data, message ->
                        homeList.add(HomeItem(typeId, "人气讲师", data))
                    }
                }
                else -> {
                }
            }

            onBizError { code, message ->
                LogUtils.e("模块数据 bizError $code $message")

            }
        }.onFailure {
            LogUtils.e("模块数据 Failure")
        }

    }

    companion object {

        internal const val TYPE_JOB_CLASS = 1
        internal const val TYPE_NEW_CLASS = 2
        internal const val TYPE_LIMIT_FREE = 3
        internal const val TYPE_ANDROID = 4
        internal const val TYPE_AI = 5
        internal const val TYPE_BD = 6
        internal const val TYPE_10W = 7
        internal const val TYPE_TEACHER = 8

    }

}

