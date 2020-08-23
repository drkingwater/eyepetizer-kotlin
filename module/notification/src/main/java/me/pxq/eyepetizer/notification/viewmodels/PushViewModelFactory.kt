package me.pxq.eyepetizer.notification.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import me.pxq.common.ApiService
import me.pxq.eyepetizer.notification.repository.NotificationRepository

/**
 * Description: 推送VM 工厂类
 * Author : pxq
 * Date : 2020/8/23 5:30 PM
 */
class PushViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PushViewModel(NotificationRepository(ApiService.instance)) as T
    }
}