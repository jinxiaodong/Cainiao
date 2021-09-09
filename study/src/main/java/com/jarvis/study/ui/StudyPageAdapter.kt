package com.jarvis.study.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jarvis.study.databinding.ItemCourseStudyBinding
import com.jarvis.study.net.StudiedRsp
import com.jarvis.study.repo.StudyRepo

/**
 * @author jinxiaodong
 * @description：
 * @date 2021/9/8
 */
class StudyPageAdapter : PagingDataAdapter<StudiedRsp.Data, StudiedVH>(differCallback) {


    companion object {
        private val differCallback = object : DiffUtil.ItemCallback<StudiedRsp.Data>() {
            override fun areItemsTheSame(
                oldItem: StudiedRsp.Data,
                newItem: StudiedRsp.Data
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: StudiedRsp.Data,
                newItem: StudiedRsp.Data
            ): Boolean {
                return oldItem == newItem
            }


        }
    }


    override fun onBindViewHolder(holder: StudiedVH, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = StudiedVH.createVH(parent)
}


class StudiedVH(private val binding: ItemCourseStudyBinding) :
    RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun createVH(parent: ViewGroup): StudiedVH {
            return StudiedVH(
                ItemCourseStudyBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }
    }


    fun bind(info: StudiedRsp.Data) {
        binding.info = info
        binding.npbProgressItemStudy.progress = info.progress.toInt()
        binding.executePendingBindings()
    }
}