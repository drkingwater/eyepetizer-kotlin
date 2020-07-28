package me.pxq.common

import android.os.Build
import me.pxq.utils.DeviceUtil
import me.pxq.utils.logi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Description: api服务类
 * Author : pxq
 * Date : 2020/7/28 8:51 PM
 */
class ApiService {

    /**
     * /api/v5/index/tab/allRec?page=0
     * &isOldUser=true
     * &udid=d9e4d30f251a4dcfb56b3465d22aa1748694f6b7
     * &udid=0851 A7AB 68D8 B30E 8117 9E82 D267 06EA 52F0 DFC3
     * &vc=6030015
     * &vn=6.3.1
     * &size=1080X1776
     * &deviceModel=HTC%20M8w
     * &first_channel=yingyongbao
     * &last_channel=yingyongbao
     * &system_version_code=23
     */
    class CommonParamsInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()
            val originalHttpUrl = originalRequest.url()
            val url = originalHttpUrl.newBuilder().apply {
                addQueryParameter("udid", DeviceUtil.DEVICE_SERIAL)
                addQueryParameter("vc", "6030015")   //官方app 版本号
                addQueryParameter("vn", "6.3.1")    //官方app 版本名
                addQueryParameter("size", DeviceUtil.DISPLAY_SIZE)
                addQueryParameter("deviceModel", DeviceUtil.DEVICE_MODEL)
                addQueryParameter("first_channel", "yingyongbao")
                addQueryParameter("last_channel", "yingyongbao")
                addQueryParameter("system_version_code", "${Build.VERSION.SDK_INT}")
            }.build()
            val request = originalRequest.newBuilder().url(url)
                .method(originalRequest.method(), originalRequest.body()).build()
            logi(request.url())
            return chain.proceed(request)
        }
    }

    companion object {
        private const val BASE_URL = "http://baobab.kaiyanapp.com/"

        private val httpClient = OkHttpClient.Builder()
            .addInterceptor(CommonParamsInterceptor())  //添加公共参数拦截器
            .build()

        val instance: Api by lazy {
            Retrofit.Builder()
                .client(httpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Api::class.java)
        }
    }

}