package me.pxq.common

import me.pxq.common.data.HomePage
import retrofit2.http.GET
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
    suspend fun recommend(@Url url: String = "http://baobab.kaiyanapp.com/api/v5/index/tab/allRec?page=0"): HomePage

    /**
     * 获取首页-发现数据
     */
    @GET("api/v7/index/tab/discovery")
    suspend fun discovery(): HomePage


}