package com.jarvis.mine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.blankj.utilcode.util.ToastUtils
import com.jarvis.common.base.BaseActivity
import com.jarvis.mine.databinding.ActivityMineBinding
import com.jarvis.mine.widget.ItemSettingsBean

class MineActivity : BaseActivity<ActivityMineBinding>() {

    override fun getLayoutRes() = R.layout.activity_mine

    override fun initView() {
        super.initView()
        mBinding.apply {

            val itemSettingsBean = ItemSettingsBean(title = "学习卡")
            isvCard.setInfo(itemSettingsBean)

            isvCard.onClickTitle{
                isvCard.setTitle( "学习卡嗷嗷")
                ToastUtils.showShort("点击了标题")
            }
//            isvCard.setOnClickListener {
//                ToastUtils.showShort("点击了整个标题")
//            }

        }
    }
}