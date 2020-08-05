package me.pxq.luanch.home

import com.github.moduth.blockcanary.BlockCanaryContext

/**
 * Description:
 * Author : pxq
 * Date : 2020/8/5 9:12 PM
 */
class AppBlockCanaryContext : BlockCanaryContext() {

    override fun provideBlockThreshold(): Int {
        return 300
    }

    override fun displayNotification(): Boolean {
        return false
    }

}