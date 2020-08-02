package me.pxq.eyepetizer.home.repository

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
    suspend fun fetchNext(url: String) =
        request(call = {
            if (url.isEmpty()) {
                apiService.recommend()
            } else{
                apiService.recommend(url)
            }
        }, errorMsg = "请求失败")
}