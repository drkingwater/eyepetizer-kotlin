package me.pxq.eyepetizer.community.repository

import me.pxq.framework.Api
import me.pxq.framework.ApiService

/**
 * Description: 社区-推荐栏数据仓库
 * Author : pxq
 * Date : 2020/8/15 2:10 PM
 */
class CommunityRepository(private val apiService: Api) {

    /**
     * 获取社区-推荐数据
     */
    suspend fun fetchCommunityRecommend(url: String = ApiService.COMMUNITY_RECOMMEND_PAGE) = apiService.fetchCommunityRecommend(url)

    /**
     * 获取社区-关注数据
     */
    suspend fun fetchCommunityFollow(url: String = ApiService.COMMUNITY_FOLLOW_PAGE) = apiService.fetchCommunityFollow(url)

}