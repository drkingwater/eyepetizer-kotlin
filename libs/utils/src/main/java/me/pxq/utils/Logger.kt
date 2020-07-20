package me.pxq.utils

import android.util.Log

/**
 * Description: 日志工具类
 * Author : pxq
 * Date : 2020/7/14 10:41 PM
 */
//应用级别日志tag
const val APP_TAG = "Eyepetizer"
//分级日志
inline fun <reified T> T.logi(msg :String) = Log.i("$APP_TAG-${T::class.java.simpleName}", msg)

//fun logd(tag :String, msg :String) = Log.d("$APP_TAG-$tag", msg)
inline fun <reified T> T.logd(msg :String) = Log.d("$APP_TAG-${T::class.java.simpleName}", msg)

//fun loge(tag :String, msg :String) = Log.e("$APP_TAG-$tag", msg)
inline fun <reified T> T.loge( msg :String) = Log.e("$APP_TAG-${T::class.java.simpleName}", msg)

fun loge(tag :String, msg :String, tr : Throwable) = Log.e("$APP_TAG-$tag", msg, tr)