package com.jarvis.service.repo

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * @author jinxiaodong
 * @description：
 * @date 2021/9/3
 */
object CniaoDbHelper {


    /**
     * 获取room数据表中存储的userInfo
     * return liveData形式
     */
    fun getLiveUserInfo(context: Context) =
        CniaoDatabase.getInstance(context).userDao.queryLiveUser()

    /**
     * 以普通数据对象的形式，获取userInfo
     */
    fun getUserInfo(context: Context) =
        CniaoDatabase.getInstance(context).userDao.queryUser()


    /**
     * 删除数据表中的userInfo信息
     */

    fun deleteUserInfo(context: Context) {
        GlobalScope.launch(Dispatchers.IO) {
            getUserInfo(context)?.let {
                CniaoDatabase.getInstance(context).userDao.deleteUser(it)
            }
        }
    }


    /**
     * 新增用户数据到数据表
     */
    fun insertUserInfo(context: Context, user: CniaoUserInfo) {
        GlobalScope.launch(Dispatchers.IO) {
            CniaoDatabase.getInstance(context).userDao.insertUser(user)
        }
    }


}