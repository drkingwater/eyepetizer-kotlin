package me.pxq.eyepetizer.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import me.pxq.common.ApiService
import me.pxq.common.db.EyeDatabase
import me.pxq.eyepetizer.home.repository.HomeRepository

/**
 * Description: HomeViewModel工厂类
 * Author : pxq
 * Date : 2020/7/18 10:44 PM
 */
class HomeViewModelFactory(private val repository: HomeRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(repository) as T
    }

    companion object {
        fun get(context: Context): HomeViewModelFactory =
            HomeViewModelFactory(HomeRepository(ApiService.instance, EyeDatabase.get(context).homeDAO()))

    }
}