package me.pxq.network

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
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
            ApiResult.Success(call())
        } catch (e: Exception) {
            ApiResult.Error(Exception(errorMsg, e).also { e.printStackTrace() })
        }
    }


@ExperimentalCoroutinesApi
fun <T : Any> requestFlow(
    call: suspend () -> T,
    onSuccess: (T) -> Unit,
    onStart: () -> Unit = {},
    onComplete: () -> Unit = {},
    onError: (Throwable) -> Unit = {}
) = flow {
    emit(call())
}.flowOn(Dispatchers.IO)
    .onStart {
        onStart()
    }.catch { ex ->
        Log.e("requestFlow", "onError: ", ex)
        onError(ex)
    }.onCompletion {
        onComplete()
    }.onEach {
        onSuccess(it)
    }