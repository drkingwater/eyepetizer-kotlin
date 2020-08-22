package me.pxq.common

import me.pxq.common.data.HomePage
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * Description: 数据接口
 * Author : pxq
 * Date : 2020/7/16 9:36 PM
 */
interface Api {

    /**
     * 获取首页-推荐数据
     */
    @GET
    suspend fun fetchRecommend(@Url url: String = ApiService.HOME_RECOMMEND_PAGE): HomePage

    /**
     * 获取首页-发现数据
     */
    @GET("api/v7/index/tab/discovery")
    suspend fun fetchDiscovery(): HomePage

    /**
     * 获取首页-日报数据
     */
    @GET
    suspend fun fetchDaily(@Url url: String = ApiService.HOME_DAILY_PAGE): HomePage


    /**
     * 获取视频详情-推荐列表
     */
    @GET("api/v4/video/related")
    suspend fun fetchVideoRelated(@Query("id") videoId: String): HomePage

    /**
     * 获取视频详情-评论列表
     */
    @GET("api/v2/replies/video")
    suspend fun fetchVideoReplies(@Query("videoId") videoId: String): HomePage

    /**
     * 加载更多视频详情-评论列表
     */
    @GET
    suspend fun fetchMoreVideoReplies(@Url url: String): HomePage

    /**
     * 获取社区-推荐数据
     */
    @GET
    suspend fun fetchCommunityRecommend(@Url url: String): HomePage

    /**
     * 获取社区-关注数据
     */
    @GET
    suspend fun fetchCommunityFollow(@Url url: String): HomePage


}