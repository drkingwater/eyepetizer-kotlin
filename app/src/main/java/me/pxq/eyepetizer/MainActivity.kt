package me.pxq.eyepetizer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.pxq.common.HomePage
import me.pxq.eyepetizer.home.HomeApi
import me.pxq.utils.logd
import me.pxq.utils.logi

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        testGet()
        logi(TAG, "onCreate")
    }

    private fun testGet() {
        lifecycleScope.launch {
            findViewById<TextView>(R.id.test_tv).text = get().toString()
        }
    }

    private suspend fun get(): HomePage = withContext(Dispatchers.IO) {
        HomeApi.instance.index().also {
            logd(TAG, it.toString())
        }
    }


    companion object {
        private const val TAG = "MainActivity"
    }
}