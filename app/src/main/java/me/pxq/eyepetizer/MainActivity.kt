package me.pxq.eyepetizer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import me.pxq.common.Api
import me.pxq.common.db.EyeDatabase
import me.pxq.eyepetizer.home.HomeViewModel
import me.pxq.eyepetizer.home.HomeViewModelFactory
import me.pxq.eyepetizer.home.repository.HomeRepository
import me.pxq.network.ApiResult
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
        observeData()
        logi("onCreate")
        lifecycleScope.launchWhenStarted {
            viewModel.fetchHomeData()
        }
    }

    /**
     * 观察数据变化
     */
    private fun observeData() {
        viewModel.homeData.observe(this, Observer { result ->
            when (result) {
                //请求成功
                is ApiResult.Success -> findViewById<TextView>(R.id.test_tv).text =
                    result.data.toString()
                //请求失败
                is ApiResult.Error -> Toast.makeText(
                    this,
                    result.exception.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }


    companion object {
        private const val TAG = "MainActivity"
    }
}