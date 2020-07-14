package me.pxq.eyepetizer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import me.pxq.utils.logd
import me.pxq.utils.loge
import me.pxq.utils.logi

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        logi(TAG, "onCreate")
    }

    companion object{
        private const val TAG = "MainActivity"
    }
}