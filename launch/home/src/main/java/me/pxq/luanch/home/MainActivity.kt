package me.pxq.luanch.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import me.pxq.eyepetizer.home.ui.IndexFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragment =
            supportFragmentManager.findFragmentByTag(IndexFragment::class.java.simpleName)
        if (fragment == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, IndexFragment(), IndexFragment::class.java.simpleName).commit()
        }
    }
}
