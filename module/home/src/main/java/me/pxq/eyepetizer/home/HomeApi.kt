package me.pxq.eyepetizer.home

import me.pxq.common.HomePage
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/**
 * Description: 首页相关api
 * Author : pxq
 * Date : 2020/7/15 9:51 PM
 */
interface HomeApi {


    @GET(HOME)
    suspend fun index(): HomePage

    companion object {
        private const val BASE_URL = "http://baobab.kaiyanapp.com/"
        private const val HOME = "api/v4/tabs/selected"

        val instance: HomeApi by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(HomeApi::class.java)
        }

    }

}