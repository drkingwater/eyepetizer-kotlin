package me.pxq.framework.ui.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.pxq.framework.R

/**
 * Description:
 * Author : pxq
 * Date : 2020/8/2 10:24 PM
 */
class TheEndHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.rv_item_the_end, parent, false)
)