package com.jarvis.common.dp

import androidx.room.*

/**
 * @author jinxiaodong
 * @description：
 * @date 2021/8/16
 */
@Dao
interface UserDao {

    //查询所有数据，若返回liveData则为 LiveData<List<DbUser>>
    @Query(value = "select * from db_user")
    fun getAll(): List<DbUser?>?


    @Query("SELECT * FROM db_user WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<DbUser?>?


    @Query(
        "SELECT *FROM db_user WHERE uname LIKE :name AND " +
                "age LIKE :age LIMIT 1"
    )
    fun findByName(name: String, age: Int): DbUser?


    @Query("select * from db_user where uid like :id")
    fun getUserById(id: Int): DbUser?

    @Insert
    fun insertAll(vararg users: DbUser?) //支持可变参数

    @Delete
    fun delete(user: DbUser?) //删除指定的user

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(user: DbUser?) //更新，若出现冲突，则使用替换策略，还有其他策略可选择

}