package me.pxq.utils

import com.google.gson.Gson
import java.lang.reflect.Type

/**
 * Description:
 * Author : pxq
 * Date : 2020/7/16 10:27 PM
 */
object JsonUtil {

    val gson : Gson = Gson()

    fun <T> fromGson(json: String, typeOfT : Type) = gson.fromJson<T>(json, typeOfT)

    fun toJson(src : Any) = gson.toJson(src)

}