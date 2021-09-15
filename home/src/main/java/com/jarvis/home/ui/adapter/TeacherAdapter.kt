package com.jarvis.home.ui.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jarvis.home.databinding.ItemTeacherBinding
import com.jarvis.home.net.PopTeacherList

/**
 * @author jinxiaodong
 * @description：
 * @date 2021/9/15
 */
class TeacherAdapter(private val mList: PopTeacherList) :
    RecyclerView.Adapter<TeacherAdapter.TeacherVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TeacherVH.create(parent)

    override fun onBindViewHolder(holder: TeacherVH, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount() = mList.size


    class TeacherVH(private val binding: ItemTeacherBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun create(parent: ViewGroup): TeacherVH {
                return TeacherVH(
                    ItemTeacherBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }

        fun bind(info: PopTeacherList.PopTeacherListItem) {
            binding.info = info
            binding.tvOldPriceItemCourse.paintFlags += Paint.STRIKE_THRU_TEXT_FLAG
            binding.executePendingBindings()
        }
    }

}