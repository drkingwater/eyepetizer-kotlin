package me.pxq.utils

import android.content.Context
import androidx.core.content.edit

/**
 * Description: sp相关工具类
 * Author : pxq
 * Date : 2020/7/28 9:21 PM
 */
object SpUtil {

    fun getString(context: Context, key: String) =
        context.getSharedPreferences("${context.packageName}_eye_sp", Context.MODE_PRIVATE)
            .getString(key, "") ?: ""


    fun putString(context: Context, key: String, value: String) {
        context.getSharedPreferences("${context.packageName}_eye_sp", Context.MODE_PRIVATE).edit {
            putString(key, value)
            apply()
        }
    }

}