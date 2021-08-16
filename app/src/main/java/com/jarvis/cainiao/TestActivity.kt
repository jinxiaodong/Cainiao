package com.jarvis.cainiao

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.jarvis.common.dp.DbUser
import com.jarvis.common.dp.UserDao
import com.jarvis.common.dp.UserDatabase
import kotlin.text.StringBuilder

class TestActivity : AppCompatActivity() {

    private val tvInsert: TextView by lazy { findViewById<TextView>(R.id.tv_insert_room) }
    private val tvDelete: TextView by lazy { findViewById<TextView>(R.id.tv_delete_room) }
    private val tvUpdate: TextView by lazy { findViewById<TextView>(R.id.tv_update_room) }
    private val tvQueryID: TextView by lazy { findViewById<TextView>(R.id.tv_query_id_room) }
    private val tvSize: TextView by lazy { findViewById<TextView>(R.id.tv_size_room) }
    private val tvAll: TextView by lazy { findViewById<TextView>(R.id.tv_all_room) }


    private lateinit var instance: UserDatabase
    private lateinit var userDao: UserDao


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        instance = UserDatabase.getInstance(this)
        deleteDatabase(UserDatabase.DB_NAME)
        userDao = instance.userDao
    }


    fun insert(view: View) {

        var sb = StringBuilder()
        var user: DbUser

        for (i in 0..4) {
            user = DbUser()
            user.age = 20 + i
            user.city = "北京"
            user.name = "小家$i"
            user.isSingle = i % 2 == 0
            userDao.insertAll(user)
            sb.appendLine(user.toString())
        }

        tvInsert.text = sb.toString()
        getAll()
    }

    private fun getAll() {

        val list = userDao.getAll()
        var stringBuilder = StringBuilder()

        list?.forEach {
            stringBuilder
                .append("uid:").append(it?.uid)
                .append("姓名：").append(it?.name)
                .append("姓名：").append(it?.name)
                .append("年龄:").append(it?.age)
                .append("城市").append(it?.city)
                .append("Single:").append(it?.isSingle)
                .appendLine()
        }
        val text = "All Size : ${list?.size}"
        tvSize.text = text
        tvAll.text = stringBuilder.toString()

    }


    fun delete(view: View?) {
        val user = userDao.findByName("小家" + 3, 23)?.let {
            userDao.delete(it)
            tvDelete.text = it.toString()
            getAll()

        }
        //
    }

    fun update(view: View?) {
        val user = userDao.findByName("小家" + 2, 22) ?: return
        user.age = 33
        user.city = "上海"
        user.name = "张三"
        user.isSingle = true
        userDao.update(user)
        tvUpdate.text = user.toString()
        getAll()
    }

    fun queryId(view: View?) {
        val userById = userDao.getUserById(3)
        if (userById != null) {
            tvQueryID.text = userById.toString()
        } else {
            Toast.makeText(this, "id=3 的user查询不到", Toast.LENGTH_SHORT).show()
        }
        //为了显示操作后的更新数据
        getAll()
    }

    fun queryAll(view: View?) {
        getAll()
    }


    override fun onDestroy() {
        super.onDestroy()
        instance.clearAllTables()
    }

}