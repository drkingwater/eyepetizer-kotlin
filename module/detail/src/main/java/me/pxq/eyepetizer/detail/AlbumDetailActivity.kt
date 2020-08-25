package me.pxq.eyepetizer.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.alibaba.android.arouter.facade.annotation.Route
import me.pxq.common.model.Item
import me.pxq.common.router.RouterHub
import me.pxq.eyepetizer.detail.databinding.DetailActivityAlbumBinding

/**
 * Description: 图片集展示
 * Author : pxq
 * Date : 2020/8/25 9:51 PM
 */
@Route(path = RouterHub.DETAIL_ALBUM)
class AlbumDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<DetailActivityAlbumBinding>(this, R.layout.detail_activity_album).apply {
            album = intent.getSerializableExtra(RouterHub.DETAIL_ALBUM_PARAM) as Item
            executePendingBindings()
        }
    }
    
}