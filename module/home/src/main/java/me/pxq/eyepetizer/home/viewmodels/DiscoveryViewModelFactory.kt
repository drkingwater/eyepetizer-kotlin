package me.pxq.eyepetizer.home.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import me.pxq.framework.ApiService
import me.pxq.framework.db.EyeDatabase
import me.pxq.eyepetizer.home.repository.HomeRepository

/**
 * Description: HomeViewModel工厂类
 * Author : pxq
 * Date : 2020/7/18 10:44 PM
 */
class DiscoveryViewModelFactory(private val repository: HomeRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DiscoveryViewModel(repository) as T
    }

    companion object {
        fun get(context: Context): DiscoveryViewModelFactory =
            DiscoveryViewModelFactory(
                HomeRepository(ApiService.instance, EyeDatabase.get(context).homeDAO())
            )
    }
}