package me.pxq.eyepetizer.home.repository

import me.pxq.framework.Api
import me.pxq.framework.ApiService
import me.pxq.framework.db.HomeDAO

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
    suspend fun fetchRecommend(nextPage: String = ApiService.HOME_RECOMMEND_PAGE) = apiService.fetchRecommend(nextPage)

    /**
     * 获取发现数据
     */
    suspend fun fetchDiscovery() = apiService.fetchDiscovery()

    /**
     * 获取首页-日报数据
     */
    suspend fun fetchDaily(nextPage: String = ApiService.HOME_DAILY_PAGE) = apiService.fetchDaily(nextPage)
}