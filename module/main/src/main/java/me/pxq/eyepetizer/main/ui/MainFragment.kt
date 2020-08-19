package me.pxq.eyepetizer.main.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import me.pxq.eyepetizer.main.R

/**
 * Description:
 * Author : pxq
 * Date : 2020/8/19 11:08 PM
 */
class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_activity_main, container, false)
    }



}