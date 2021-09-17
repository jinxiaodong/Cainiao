package com.jarvis.study.ui.play

import android.content.Context
import android.content.Intent
import cn.jzvd.Jzvd
import com.blankj.utilcode.util.ToastUtils
import com.jarvis.common.base.BaseActivity
import com.jarvis.study.R
import com.jarvis.study.databinding.ActivityClassPlayBinding
import com.jarvis.study.net.HasCoursePermission
import com.jarvis.study.net.StudiedRsp
import kotlinx.android.synthetic.main.activity_class_play.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ClassPlayActivity : BaseActivity<ActivityClassPlayBinding>() {

    companion object {

        private const val INTENT_KEY_COURSE_INFO = "course_info"
        fun openPlay(context: Context, course: StudiedRsp.Data) {
            context.startActivity(Intent(context, ClassPlayActivity::class.java).also {
                it.putExtra(INTENT_KEY_COURSE_INFO, course)
            })
        }
    }

    private val viewModel: ClassPlayViewModel by viewModel()

    override fun getLayoutRes() = R.layout.activity_class_play

    private val lessonSectionList = mutableListOf<LessonSection>()

    private val lessonAdapter = ClassAdapter { clickedItem ->
        lessonSectionList.forEach {
            it.isPlaying.set(it.key == clickedItem.key)
        }
        //点击了某个课时，用于播放
        clickedItem.key?.let {
            viewModel.getPlayInfo(clickedItem.key)
        }
    }


    override fun initConfig() {
        super.initConfig()
        val courseInfo =
            intent.getParcelableExtra<StudiedRsp.Data>(INTENT_KEY_COURSE_INFO)
                ?: return ToastUtils.showShort("课程Id为空，是不行滴")

        mBinding.vm = viewModel
        mBinding.info = courseInfo
        mBinding.adapter = lessonAdapter
        mBinding.jzVideoStudy.reset()

        viewModel.apply {
            //课程权限
            checkPermission(courseInfo.id)

            //livePermission
            liveCoursePermission.observerKt { p ->
                if (p?.is_true == HasCoursePermission.HAS_COURSE_PERMISSION) {
                    //有权限，则再获取对应的课时章节
                    getChapters(courseInfo.id)
                } else {
                    ToastUtils.showShort("似乎没有课程权限")
                }
            }
            //课时章节
            liveChapterList.observerKt { chapters ->
                lessonSectionList.clear()
                chapters?.forEach { chapter ->
                    lessonSectionList.add(
                        LessonSection(
                            LessonSection.ITEM_TYPE_TITLE,
                            "第${chapter.bsort}章 ${chapter.title}"
                        )
                    )
                    chapter.lessons?.forEach { lesson ->
                        lessonSectionList.add(
                            LessonSection(
                                LessonSection.ITEM_TYPE_CONTENT,
                                "${chapter.bsort}-${lesson.bsort} ${lesson.name}",
                                lesson.key,
                                lesson.is_free == 1
                            )
                        )
                    }
                }
                //update adapter
                lessonAdapter.updateList(lessonSectionList)
            }
            //播放信息
            livePlayInfo.observerKt { info ->
                val vUrl = "https:${info?.play_urls?.hls?.urls?.hd}"
                jz_video_study.setUp(vUrl, info?.last_play_info?.title)
                jz_video_study.startVideo()
            }
        }

    }

    override fun onBackPressed() {
        if (Jzvd.backPress()) {
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        Jzvd.releaseAllVideos()
    }
}