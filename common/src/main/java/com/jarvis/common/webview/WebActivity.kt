package com.jarvis.common.webview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.jarvis.common.BuildConfig
import com.jarvis.common.R
import com.just.agentweb.AgentWeb
import com.just.agentweb.AgentWebView
import com.just.agentweb.DefaultWebClient
import kotlinx.android.synthetic.main.activity_webview.*

/**
 * @author jinxiaodong
 * @description：
 * @date 2021/9/15
 */
class WebActivity : AppCompatActivity() {


    companion object {
        fun openUrl(context: Context, url: String) {
            context.startActivity(Intent(context, WebActivity::class.java).also {
                it.putExtra("url", url)
            })
        }
    }


    private lateinit var mAgentWeb: AgentWeb


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(
                ll_webview,
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
            .useDefaultIndicator(resources.getColor(R.color.colorAccent))
            .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
            .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
            .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)
            .interceptUnkownUrl()
            .createAgentWeb()
            .ready().get()

        val url = intent.getStringExtra("url")
        mAgentWeb.urlLoader.loadUrl(url)
        AgentWebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG)
    }


    /**
     * 处理按键的事件，来响应对应的逻辑
     *
     * @param keyCode 按键keycode
     * @param event   事件
     * @return true 处理
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            true
        } else super.onKeyDown(keyCode, event)
    }

    /**
     * Activity暂停状态，停止web’的加载
     */
    override fun onPause() {
        mAgentWeb.webLifeCycle.onPause()
        super.onPause()

    }

    /**
     * Activity的resume，同步web的状态resume
     */
    override fun onResume() {
        mAgentWeb.webLifeCycle.onResume()
        super.onResume()
    }

}