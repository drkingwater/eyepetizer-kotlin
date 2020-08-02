package me.pxq.eyepetizer.home.repository

import me.pxq.common.Api
import me.pxq.common.db.HomeDAO
import me.pxq.network.request

/**
 * Description: 首页Repository，协调数据获取
 * Author : pxq
 * Date : 2020/7/16 10:00 PM
 */
class HomeRepository(private val apiService: Api, private val homeDAO: HomeDAO) {

    /**
     * 获取推荐数据
     * [nextPage]:下一页数据请求地址
     */
    suspend fun fetchRecommend(nextPage: String) =
        request(call = {
            if (nextPage.isEmpty()) {
                apiService.recommend()
            } else {
                apiService.recommend(nextPage)
            }
        }, errorMsg = "请求失败")

    /**
     * 获取发现数据
     */
    suspend fun fetchDiscovery() =
        request(call = {
            apiService.discovery()
        }, errorMsg = "请求失败")
}