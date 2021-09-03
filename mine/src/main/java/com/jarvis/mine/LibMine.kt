package com.jarvis.mine

import com.jarvis.mine.ui.MineViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * @author jinxiaodong
 * @description：koin的 mine module
 * @date 2021/9/3
 */

val moduleMine = module {
    viewModel { MineViewModel() }
}