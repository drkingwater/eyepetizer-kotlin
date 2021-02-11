package me.pxq.framework.router.service

import com.alibaba.android.arouter.facade.template.IProvider
import me.pxq.framework.viewmodel.BaseViewModel

/**
 * Description:
 * Author : pxq
 * Date : 2020/8/27 11:04 PM
 */
interface ICommunityService : IProvider {

    fun getCommunityViewModel() : BaseViewModel

}