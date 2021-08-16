package com.jarvis.cainiao

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.blankj.utilcode.util.LogUtils
import com.jarvis.common.net.RetrofitManager
import com.jarvis.common.net.model.*
import com.jarvis.common.net.support.serverRsp
import com.jarvis.common.net.support.toLiveData
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import java.io.File

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        netTest()
    }

    private fun netTest() {
        val service = RetrofitManager
            .initConfig("https://course.api.cniao5.com/")
            .getService(CniaoService::class.java)


        service.userInfo().toLiveData()
            .observe(this, Observer { LogUtils.d("retrofit userInfo ${it.toString()}") })

        val loginCall = service.login(LoginReq())

        lifecycleScope.launch {

            when (val serverRsp = loginCall.serverRsp()) {
                is ApiSuccessResponse -> {
                    LogUtils.i("apiService ${serverRsp.body}")
                }
                is ApiErrorResponse -> {
                    LogUtils.i("apiService ${serverRsp.errorMessage}")
                }
                is ApiEmptyResponse -> {
                    LogUtils.d("empty apiResponse ")
                }
            }
        }


        service.userInfo2()
            .observe(this, Observer { LogUtils.d("retrofit userInfo2 ${it.toString()}") })

    }


    data class LoginReq(val mobi: String = "18648957777", val password: String = "cn5123456")

    fun click(view: View) {
        if (view.id == R.id.text_home) {
            startActivity(Intent(this, TestActivity::class.java))
        }
    }

}


interface CniaoService {

    @POST("accounts/course/10301/login")
    fun login(@Body body: MainActivity.LoginReq): Call<NetResponse>

    @GET("member/userinfo")
    fun userInfo(): Call<NetResponse>

    @GET("member/userinfo")
    fun userInfo2(): LiveData<ApiResponse<NetResponse>>

}