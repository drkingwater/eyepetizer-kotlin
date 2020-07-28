package me.pxq.eyepetizer.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import me.pxq.eyepetizer.home.R

/**
 * Description: 推荐
 * Author : pxq
 * Date : 2020/7/27 10:30 PM
 */
class DailyFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment_recommend, container, false)
    }

    companion object{
        @JvmStatic
        fun newInstance() = DailyFragment()
    }

}