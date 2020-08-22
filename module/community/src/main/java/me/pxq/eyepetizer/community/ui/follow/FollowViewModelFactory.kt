package me.pxq.eyepetizer.community.ui.follow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import me.pxq.common.ApiService
import me.pxq.eyepetizer.community.repository.CommunityRepository

/**
 * Description: [FollowViewModel] 工厂方法
 * Author : pxq
 * Date : 2020/8/15 2:44 PM
 */
class FollowViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FollowViewModel(CommunityRepository(ApiService.instance)) as T
    }
}