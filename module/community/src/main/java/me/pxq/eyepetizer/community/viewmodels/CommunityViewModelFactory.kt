package me.pxq.eyepetizer.community.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import me.pxq.common.ApiService
import me.pxq.eyepetizer.community.repository.CommunityRepository

/**
 * Description: [CommunityViewModel] 工厂方法
 * Author : pxq
 * Date : 2020/8/15 2:44 PM
 */
class CommunityViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CommunityViewModel(CommunityRepository(ApiService.instance)) as T
    }
}