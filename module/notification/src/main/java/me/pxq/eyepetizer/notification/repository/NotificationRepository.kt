package me.pxq.eyepetizer.notification.repository

import me.pxq.framework.Api
import me.pxq.framework.ApiService
import retrofit2.http.Url

/**
 * Description:
 * Author : pxq
 * Date : 2020/8/23 5:17 PM
 */
class NotificationRepository(private val apiService: Api) {

    suspend fun fetchNotificationPush(@Url url: String = ApiService.NOTIFICATION_PUSH_PAGE) = apiService.fetchNotificationPush(url)

}