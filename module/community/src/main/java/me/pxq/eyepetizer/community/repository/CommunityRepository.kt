package me.pxq.eyepetizer.community.repository

import me.pxq.common.Api
import me.pxq.common.ApiService
import me.pxq.network.request

/**
 * Description: 社区-推荐栏数据仓库
 * Author : pxq
 * Date : 2020/8/15 2:10 PM
 */
class CommunityRepository(private val apiService: Api) {

    /**
     * 获取社区-推荐数据
     */
    suspend fun fetchCommunityRecommend(url: String = ApiService.COMMUNITY_RECOMMEND_PAGE) =
        request({
            apiService.fetchCommunityRecommend(url)
        }, "请求失败")

}