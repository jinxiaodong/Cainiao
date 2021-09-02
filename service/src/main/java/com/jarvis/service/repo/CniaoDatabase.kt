package com.jarvis.service.repo

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author jinxiaodong
 * @description：
 * @date 2021/9/2
 */


//1、entity的定义

@Entity(tableName = "tb_cniao_user")
data class CniaoUserInfo(
    @PrimaryKey
    val id: Int,//主键
    val course_permission: Boolean,
    val token: String?,
    @Embedded
    val user: User?
) {
    @Keep
    data class User(
        @ColumnInfo(name = "cniao_user_id")
        val id: Int,//用户id
        val logo_url: String?,//用户头像
        val reg_time: String?,//用户注册时间
        val username: String?//用户名
    )
}
