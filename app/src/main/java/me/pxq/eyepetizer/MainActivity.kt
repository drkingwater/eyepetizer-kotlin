package me.pxq.eyepetizer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.launcher.ARouter
import me.pxq.common.Api
import me.pxq.common.db.EyeDatabase
import me.pxq.common.router.RouterHub
import me.pxq.eyepetizer.home.HomeViewModel
import me.pxq.eyepetizer.home.HomeViewModelFactory
import me.pxq.eyepetizer.home.repository.HomeRepository
import me.pxq.network.ApiResult
import me.pxq.utils.logd
import me.pxq.utils.logi

/**
 * 测试用
 */
class MainActivity : AppCompatActivity() {
    //测试用ViewModel
    private val viewModel: HomeViewModel by viewModels { HomeViewModelFactory.get(this@MainActivity) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        logi("onCreate")
        ARouter.getInstance().build(RouterHub.MAIN_HONE).navigation()?.run {
            logd("router success")
        }
    }



    companion object {
        private const val TAG = "MainActivity"
    }
}