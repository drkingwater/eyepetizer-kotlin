package me.pxq.eyepetizer.detail.repository

import me.pxq.common.Api
import me.pxq.network.request
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * Description: 视频详情页数据源
 * Author : pxq
 * Date : 2020/8/9 6:43 PM
 */
class VideoDetailRepository(private val apiService: Api) {

    /**
     * 获取视频相关推荐
     */
    suspend fun fetchVideoRelated(id: String) =
        request({
            apiService.fetchVideoRelated(id)
        }, "请求失败")

    /**
     * 获取视频评论
     */
    suspend fun fetchVideoReplies(@Query("id") videoId: String) =
        request({
            apiService.fetchVideoReplies(videoId)
        }, "请求失败")

    /**
     * 加载更多评论
     */
    suspend fun fetchMoreVideoReplies(@Url url: String) = apiService.fetchMoreVideoReplies(url)

}