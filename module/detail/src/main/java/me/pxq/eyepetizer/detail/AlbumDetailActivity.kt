package me.pxq.eyepetizer.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.facade.annotation.Route
import kotlinx.android.synthetic.main.detail_activity_album.*
import me.pxq.framework.model.Item
import me.pxq.framework.router.RouterHub
import me.pxq.eyepetizer.detail.adapters.AlbumVpAdapter
import me.pxq.eyepetizer.detail.databinding.DetailActivityAlbumBinding

/**
 * Description: 图片集展示
 * Author : pxq
 * Date : 2020/8/25 9:51 PM
 */
@Route(path = RouterHub.DETAIL_ALBUM)
class AlbumDetailActivity : AppCompatActivity() {

    private lateinit var binding: DetailActivityAlbumBinding

//    private lateinit var recommendViewModel : BaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<DetailActivityAlbumBinding>(
            this,
            R.layout.detail_activity_album
        ).apply {
            val theAlbum = intent.getSerializableExtra(RouterHub.DETAIL_ALBUM_PARAM) as Item
//            recommendViewModel = intent.getSerializableExtra("viewmodel") as BaseViewModel

            album = theAlbum

            with(vp_album) {
                orientation = ViewPager2.ORIENTATION_VERTICAL
                offscreenPageLimit = 2
                adapter = AlbumVpAdapter(mutableListOf(theAlbum))
            }

            executePendingBindings()
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, me.pxq.framework.R.anim.slide_bottom_out)
    }

}