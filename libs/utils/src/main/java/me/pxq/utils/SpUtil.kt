package me.pxq.utils

import android.content.Context
import androidx.core.content.edit

/**
 * Description: sp相关工具类
 * Author : pxq
 * Date : 2020/7/28 9:21 PM
 */
object SpUtil {

    fun getString(context: Context, key: String, def: String = "") =
        context.getSharedPreferences("${context.packageName}_eye_sp", Context.MODE_PRIVATE)
            .getString(key, def) ?: def


    fun putString(context: Context, key: String, value: String) {
        context.getSharedPreferences("${context.packageName}_eye_sp", Context.MODE_PRIVATE).edit {
            putString(key, value)
            apply()
        }
    }

    fun putInt(context: Context, key: String, value: Int) {
        context.getSharedPreferences("${context.packageName}_eye_sp", Context.MODE_PRIVATE).edit {
            putInt(key, value)
            apply()
        }
    }

    fun getInt(context: Context, key: String, def: Int = 0) =
        context.getSharedPreferences("${context.packageName}_eye_sp", Context.MODE_PRIVATE)
            .getInt(key, def)

}