package me.pxq.eyepetizer.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.*
import me.pxq.common.data.Item
import me.pxq.common.ui.view.TheEndHolder
import me.pxq.eyepetizer.home.R
import me.pxq.eyepetizer.home.databinding.*
import me.pxq.eyepetizer.home.decoration.MarginDecoration
import me.pxq.eyepetizer.home.viewmodel.BaseViewModel
import me.pxq.utils.extensions.dp2px
import me.pxq.utils.logd

/**
 * Description: [me.pxq.eyepetizer.home.ui.IndexFragment] 推荐栏 Rv Adapter
 * Author : pxq
 * Date : 2020/7/29 9:57 PM
 */
class IndexRvAdapter(val actionVM: BaseViewModel, var items: MutableList<Item> = mutableListOf()) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return IndexRvHelper.getItemViewType(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == IndexRvHelper.VIEW_HOLDER_TYPE_THE_END) return TheEndHolder(parent)
        //获取相应的布局文件
        val layoutId = IndexRvHelper.getItemLayout(viewType)
        //数据绑定
        return ItemHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                layoutId,
                parent,
                false
            ),
            viewType
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemHolder -> holder.bind(items[position])
        }
    }


    inner class ItemHolder(private val binding: ViewDataBinding, private val viewType: Int) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            when (binding) {
                is HomeRvItemAutoPlayVideoAdBinding -> {  //自动播放广告
                    with(binding) {
                        videoAd = item
                        executePendingBindings()
                    }
                }
                is HomeRvItemTextcardRightandleftBinding -> {  //标题 header7 ：如每日开眼精选
                    with(binding) {
                        header = item
                        viewModel = actionVM
                        executePendingBindings()
                    }
                }
                is HomeRvItemTextcardTextcardHeader5Binding -> { //标题header5
                    with(binding) {
                        header = item
                        viewModel = actionVM
                        executePendingBindings()
                    }
                }
                is HomeRvItemTextcardTextcardFooter2Binding -> {  //底部文字
                    with(binding) {
                        header = item
                        viewModel = actionVM
                        executePendingBindings()
                    }
                }
                is HomeRvItemFollowcardFollowcardBinding -> {  //视频:如每日开眼精选
                    with(binding) {
                        daily = item
                        viewModel = actionVM
                        executePendingBindings()
                    }
                }
                is HomeRvItemInfocardInfocardBinding -> {
                    with(binding) {
                        logd("info card...")
                        //绑定rv adapter
                        if (rvBanner.adapter == null) {
                            rvBanner.run {
                                isNestedScrollingEnabled = true
                                //设置分割线
                                addItemDecoration(MarginDecoration(bottom = 13f.dp2px.toInt()))
                                //布局方式
                                layoutManager = LinearLayoutManager(this.context)
                                //优化绘制
                                setHasFixedSize(true)
                                //设置adapter
                                adapter = InfoCardRvAdapter(actionVM)
                            }
                        }
                        //更新数据
                        (rvBanner.adapter as InfoCardRvAdapter).submitList(item.data.bannerList)
                        info = item
                        executePendingBindings()
                    }
                }
                is HomeRvItemVideoSmallCardBinding -> {
                    with(binding) {
                        video = item
                        viewModel = actionVM
                        executePendingBindings()
                    }
                }
                is HomeRvItemTextcardWithTagIdBinding -> {
                    with(binding) {
                        titleAction = item
                        viewModel = actionVM
                        executePendingBindings()
                    }
                }
                is HomeRvItemBannerBinding -> {
                    with(binding) {
                        banner = item
                        viewModel = actionVM
                        executePendingBindings()
                    }
                }
                is HomeRvItemUgsSelectioncardBinding -> {
                    with(binding) {
                        collection = item
                        viewModel = actionVM
                        executePendingBindings()
                    }
                }
                is HomeRvItemBriefcardTagBinding -> {
                    with(binding) {
                        brief = item
                        viewModel = actionVM
                        executePendingBindings()
                    }
                }
                is HomeRvItemBriefcardTopBinding -> {
                    with(binding) {
                        topic = item
                        viewModel = actionVM
                        executePendingBindings()
                    }
                }
                is HomeRvItemHorScrollcardBinding -> {  //首页-发现 水平滚动banner
                    with(binding) {
                        if (rvBanner.adapter == null) {
                            rvBanner.run {
                                isNestedScrollingEnabled = true
                                //设置分割线
                                addItemDecoration(MarginDecoration(right = 8f.dp2px.toInt()))
                                //布局方式
                                layoutManager = LinearLayoutManager(
                                    this.context,
                                    RecyclerView.HORIZONTAL,
                                    false
                                )
                                //优化绘制
                                setHasFixedSize(true)
                                //设置adapter
                                adapter = IvBannerAdapter(actionVM)
                            }
                        }
                        //更新数据
                        (rvBanner.adapter as IvBannerAdapter).submitList(item.data.itemList)
                        executePendingBindings()
                    }
                }
                is HomeRvItemSpecialSquareCardBinding -> {  //热门分类、专题策划，共用一个rv item
                    with(binding) {
                        //rv设置适配器
                        if (rvCategory.adapter == null) {
                            rvCategory.run {
                                isNestedScrollingEnabled = true
                                //设置分割线
                                addItemDecoration(
                                    MarginDecoration(
                                        right = 5f.dp2px.toInt(),
                                        bottom = 5f.dp2px.toInt()
                                    )
                                )
                                //优化绘制
                                setHasFixedSize(true)
                                //设置adapter
                                when (viewType) {  //热门分类、专题策划adapter不同
                                    IndexRvHelper.VIEW_HOLDER_TYPE_SPECIAL_SQUARE_CARD -> {  //热门分类
                                        adapter = SpecialSquareAdapter(actionVM)
                                        //布局方式
                                        layoutManager = GridLayoutManager(context, 2).apply {
                                            //水平滚动
                                            orientation = GridLayoutManager.HORIZONTAL
                                        }
                                    }
                                    else -> {   //专题策划
                                        adapter = ColumnCardAdapter(actionVM)
                                        layoutManager = GridLayoutManager(context, 2).apply {
                                            //竖直滚动
                                            orientation = GridLayoutManager.VERTICAL
                                        }
                                    }
                                }
                            }
                        }
                        //设置数据
                        collection = item
                        //设置viewModel
                        viewModel = actionVM
                        //更新数据
                        when (viewType) {  //热门分类、专题策划adapter不同
                            IndexRvHelper.VIEW_HOLDER_TYPE_SPECIAL_SQUARE_CARD -> (rvCategory.adapter as SpecialSquareAdapter).submitList(
                                item.data.itemList
                            )
                            else -> (rvCategory.adapter as ColumnCardAdapter).submitList(item.data.itemList)
                        }

                        executePendingBindings()
                    }
                }
            }

        }
    }


}