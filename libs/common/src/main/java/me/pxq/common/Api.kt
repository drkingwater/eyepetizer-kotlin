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

    @GET(INDEX)
    suspend fun index(): HomePage

    companion object {
        private const val BASE_URL = "http://baobab.kaiyanapp.com/"
        //首页
        private const val INDEX = "api/v4/tabs/selected"

        val service: Api by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Api::class.java)
        }

    }

}