package com.jarvis.common.dp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

/**
 * @author jinxiaodong
 * @description：
 * @date 2021/8/16
 */

@Entity(tableName = "db_user")
class DbUser {


    @PrimaryKey(autoGenerate = true)
    var uid = 0

    //数据表中的字段名称
    @ColumnInfo(name = "uname")
    var name: String? = null

    var city: String? = null

    var age = 0

    //如此数据表中不会有@Ignore标记的属性字段
    @Ignore
    var isSingle = false


    override fun toString(): String {
        return "DbUser(uid=$uid, name=$name, city=$city, age=$age, isSingle=$isSingle)"
    }


}