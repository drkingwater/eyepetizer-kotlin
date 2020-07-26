package me.pxq.eyepetizer.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import me.pxq.common.router.RouterHub
import me.pxq.eyepetizer.home.R

/**
 * Description: 首页fragment
 * Author : pxq
 * Date : 2020/7/20 9:56 PM
 */
@Route(path = RouterHub.MAIN_HONE)
class IndexFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment_index, null)
    }

}