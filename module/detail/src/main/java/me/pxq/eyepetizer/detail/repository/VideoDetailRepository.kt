package me.pxq.eyepetizer.detail.repository

import kotlinx.coroutines.delay
import me.pxq.common.Api
import me.pxq.network.request

/**
 * Description:
 * Author : pxq
 * Date : 2020/8/9 6:43 PM
 */
class VideoDetailRepository(private val apiService: Api) {

    suspend fun fetchVideoRelated(id: Int) =
        request({
            apiService.fetchVideoRelated(id)
        }, "请求失败")


}