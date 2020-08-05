package me.pxq.luanch.home

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.github.moduth.blockcanary.BlockCanary
import com.github.moduth.blockcanary.BlockCanaryContext
import me.pxq.utils.DeviceUtil

/**
 * Description:
 * Author : pxq
 * Date : 2020/7/20 10:22 PM
 */
class EyepetizerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        //初始化ARouter
        ARouter.init(this)
        ARouter.openDebug()
        ARouter.openLog()

        //工具类需要用到context
        DeviceUtil.application = this

        //BlockCanary
//        BlockCanary.install(this, AppBlockCanaryContext()).start()
    }

}