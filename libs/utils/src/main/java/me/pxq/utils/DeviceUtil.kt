package me.pxq.utils

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.os.Build
import android.provider.Settings
import android.text.TextUtils
import java.security.MessageDigest
import java.util.*

/**
 * Description: 设备相关工具类
 * Author : pxq
 * Date : 2020/7/28 9:01 PM
 */

object DeviceUtil {

    lateinit var application: Application

    /**
     * 屏幕尺寸,如：1280X720
     */
    val DISPLAY_SIZE by lazy {
        application.resources.displayMetrics.run {
            "${widthPixels}X${heightPixels}"
        }
    }

    /**
     * [Build.MODEL]或者unknown
     */
    val DEVICE_MODEL: String
        get() = Build.MODEL.ifEmpty { "unknown" }

    /**
     * 设备序列号
     */
    val DEVICE_SERIAL: String by lazy {
        // 随意生成的序列号会导致开眼api返回数据不同，因此写死一个
        "d9e4d30f251a4dcfb56b3465d22aa1748694f6b7"
        //本地取序列号
//        var serial = DeviceIdUtil.getDeviceId(application).also {
//            logd("serial $it")
//        }
//        if (serial.isNullOrEmpty()) {
//            //随机生成一个序列号并保存到本地
//            serial = UUID.randomUUID().toString().replace("-", "").toLowerCase(Locale.CHINA).also {
//                SpUtil.putString(application, "deviceSerial", it)
//            }
//        }
//        serial
    }

    /**
     * Android版本
     * @see [Build.VERSION.SDK_INT]
     */
    val SDK_INT = Build.VERSION.SDK_INT


}