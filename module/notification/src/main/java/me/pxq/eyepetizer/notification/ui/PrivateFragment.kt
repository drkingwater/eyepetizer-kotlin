package me.pxq.eyepetizer.notification.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.pxq.common.R
import me.pxq.common.ui.BaseFragment

/**
 * Description: 通知-私信Fragment
 * Author : pxq
 * Date : 2020/8/23 4:31 PM
 */
class PrivateFragment : BaseFragment() {

    override fun fetchData() = Unit

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_no_account, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() = PrivateFragment()

    }
}