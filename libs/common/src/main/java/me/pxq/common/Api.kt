package me.pxq.common

import me.pxq.common.data.HomePage
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/**
 * Description: 数据接口
 * Author : pxq
 * Date : 2020/7/16 9:36 PM
 */
interface Api {

    @GET("api/v5/index/tab/allRec?page=0")
    suspend fun index(): HomePage

}