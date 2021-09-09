package com.jarvis.study.utils

import androidx.databinding.BindingAdapter
import com.daimajia.numberprogressbar.NumberProgressBar

/**
 * @author jinxiaodong
 * @description：
 * @date 2021/9/8
 */
object StudyUtils {

    @JvmStatic
    fun rankStr(rank: Int): String {
        return if (rank > 0) "第${rank}名" else "千里之外"
    }

}

@BindingAdapter("app:progress_current", requireAll = false)
fun setProgress(pb: NumberProgressBar, progress: Double?) {
    pb.progress = ((progress ?: 0.0) * 100).toInt()
}