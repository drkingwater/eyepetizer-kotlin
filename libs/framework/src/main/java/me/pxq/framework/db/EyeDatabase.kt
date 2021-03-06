package me.pxq.framework.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import me.pxq.framework.model.HomePage
import me.pxq.framework.db.converter.HomeItemConverter

/**
 * Description: 数据库，缓存数据
 * Author : pxq
 * Date : 2020/7/16 9:56 PM
 */
@Database(entities = [HomePage::class], version = 1, exportSchema = false)
@TypeConverters(HomeItemConverter::class)
abstract class EyeDatabase : RoomDatabase() {
    //首页DAO
    abstract fun homeDAO(): HomeDAO


    companion object {
        lateinit var appContext: Context
        fun get(appContext: Context): EyeDatabase {
            this.appContext = appContext.applicationContext
            return instance
        }

        private val instance: EyeDatabase by lazy {
            Room.databaseBuilder(
                appContext,
                EyeDatabase::class.java, "eyedb"
            ).build()
        }
    }

}