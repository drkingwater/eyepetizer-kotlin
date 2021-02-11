package me.pxq.launch.notification

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import me.pxq.framework.model.Message
import me.pxq.eyepetizer.notification.R
import me.pxq.eyepetizer.notification.databinding.NotificationRvItemPushBinding

/**
 * Description:
 * Author : pxq
 * Date : 2020/8/23 7:38 PM
 */
class ConstraintLayoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<NotificationRvItemPushBinding>(this, R.layout.notification_rv_item_push).apply {
            message = Message("", "123", 123, "", 123, "2123", false)
        }
    }

}