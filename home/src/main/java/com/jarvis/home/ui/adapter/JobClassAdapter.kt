package com.jarvis.home.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jarvis.home.databinding.ItemJobClassBinding
import com.jarvis.home.net.JobClassList

/**
 * @author jinxiaodong
 * @description：
 * @date 2021/9/15
 */
class JobClassAdapter(private val mList: JobClassList) :
    RecyclerView.Adapter<JobClassAdapter.JobClassVH>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobClassAdapter.JobClassVH {
        return JobClassVH.create(parent)
    }

    override fun onBindViewHolder(holder: JobClassAdapter.JobClassVH, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }


    class JobClassVH(private val binding: ItemJobClassBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun create(parent: ViewGroup): JobClassVH {
                return JobClassVH(
                    ItemJobClassBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }

        fun bind(info: JobClassList.JobClassListItem) {
            binding.url = info.course?.img_url
            itemView.setOnClickListener {
//                WebActivity.openUrl(it.context, info.course?.h5site ?: "https://m.cniao5.com")
            }
            binding.executePendingBindings()
        }
    }
}