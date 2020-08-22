package me.pxq.network

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


/**
 * Description: api service请求响应基类
 * Author : pxq
 * Date : 2020/7/18 2:41 PM
 */
sealed class ApiResult<out T : Any> {

    //获取数据成功
    data class Success<out T : Any>(val data: T) : ApiResult<T>()

    //失败
    data class Error(val exception: Exception) : ApiResult<Nothing>()

}

suspend fun <T : Any> request(call: suspend () -> T, errorMsg: String): ApiResult<T> =
    withContext(Dispatchers.IO) {
        try {
            ApiResult.Success(call.invoke())
        } catch (e: Exception) {
            ApiResult.Error(Exception(errorMsg, e).also { e.printStackTrace() })
        }
    }
