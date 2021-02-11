package me.pxq.eyepetizer.notification.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.pxq.framework.R
import me.pxq.framework.ui.BaseFragment

/**
 * Description: 通知-互动Fragment
 * Author : pxq
 * Date : 2020/8/23 4:31 PM
 */
class InteractFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_no_account, container, false)
    }

    override fun fetchData() = Unit

    companion object {

        @JvmStatic
        fun newInstance() = InteractFragment()

    }
}