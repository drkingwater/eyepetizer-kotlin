package me.pxq.launch.notification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import me.pxq.eyepetizer.notification.NotificationFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragment =
            supportFragmentManager.findFragmentByTag(NotificationFragment::class.java.simpleName)
        if (fragment == null) {
            supportFragmentManager.beginTransaction()
                .add(
                    R.id.fragment_container,
                    NotificationFragment(),
                    NotificationFragment::class.java.simpleName
                ).commit()
        }
    }
}
