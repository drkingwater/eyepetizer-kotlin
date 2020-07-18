package me.pxq.eyepetizer.home.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.pxq.common.Api
import me.pxq.common.data.HomePage
import me.pxq.common.db.HomeDAO
import me.pxq.network.ApiResult
import me.pxq.network.request

/**
 * Description: 首页Repository，协调数据获取
 * Author : pxq
 * Date : 2020/7/16 10:00 PM
 */
class HomeRepository(private val apiService: Api, private val homeDAO: HomeDAO) {


    /**
     * 获取首页数据
     */
    suspend fun getHomeData(
        dirty: Boolean
    ): ApiResult<HomePage> {
        return withContext(Dispatchers.IO) {
            val homeData = homeDAO.getHomeData()
            when {
                //判断数据库是否有缓存
                homeData == null || dirty -> request(call = {
                    apiService.index()
                }, errorMsg = "请求失败").also {
                    //保存到数据库
                    if (it is ApiResult.Success) homeDAO.save(it.data)
                }
                else -> ApiResult.Success(homeData)
            }
        }

    }


}