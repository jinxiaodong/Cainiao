package com.jarvis.course.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jarvis.course.databinding.ItemFooterCourseBinding

/**
 * @author jinxiaodong
 * @description：
 * @date 2021/9/14
 */
class CourseLoadAdapter(private val adapter: CourseAdapter) :
    LoadStateAdapter<CourseLoadAdapter.FooterVH>() {


    override fun onBindViewHolder(holder: CourseLoadAdapter.FooterVH, loadState: LoadState) {
        holder.bindState(loadState, adapter)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): CourseLoadAdapter.FooterVH {
        return FooterVH(
            ItemFooterCourseBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    class FooterVH(private val binding: ItemFooterCourseBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bindState(loadState: LoadState, adapter: CourseAdapter) {

            when (loadState) {

                is LoadState.Error -> {
                    binding.pbFooterCourse.visibility = View.GONE
                    binding.tvFooterCourse.visibility = View.VISIBLE
                    binding.tvFooterCourse.text = "Load Failed, Tap Retry"
                    binding.tvFooterCourse.setOnClickListener {
                        adapter.retry()
                    }
                }
                is LoadState.Loading -> {
                    binding.pbFooterCourse.visibility = View.VISIBLE
                    binding.tvFooterCourse.visibility = View.VISIBLE
                    binding.tvFooterCourse.text = "Loading~~"
                }
                is LoadState.NotLoading -> {
                    binding.pbFooterCourse.visibility = View.GONE
                    binding.tvFooterCourse.visibility = View.GONE
                }
            }
        }
    }
}

