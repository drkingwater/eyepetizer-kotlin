package me.pxq.common.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import me.pxq.common.data.HomePage

/**
 * Description: 首页数据DAO类
 * Author : pxq
 * Date : 2020/7/16 9:55 PM
 */
@Dao
interface HomeDAO {
    @Query("SELECT * FROM home")
    fun getHomeData() : HomePage

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(homePage: HomePage)

}