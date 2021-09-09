package com.jarvis.study.net

import com.jarvis.service.network.BaseCniaoRsp
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author jinxiaodong
 * @description：
 * @date 2021/9/8
 */
interface StudyService {

    /**
     * 用户学习详情
     */
    @GET("/member/study/info")
    fun getStudyInfo(): Call<BaseCniaoRsp>


    /**
     * 用户学习过的课程列表
     */
    @GET("/member/courses/studied")
    fun getStudyList(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 10
    ): Call<BaseCniaoRsp>


    /**
     * 用户购买的课程
     */
    @GET("/member/courses/bought")
    fun getBoughtCourse(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 10
    ): Call<BaseCniaoRsp>

}