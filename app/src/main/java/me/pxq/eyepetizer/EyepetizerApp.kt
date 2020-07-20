package me.pxq.eyepetizer

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter

/**
 * Description:
 * Author : pxq
 * Date : 2020/7/20 10:22 PM
 */
class EyepetizerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        ARouter.init(this)
        ARouter.openDebug()
        ARouter.openLog()
    }

}