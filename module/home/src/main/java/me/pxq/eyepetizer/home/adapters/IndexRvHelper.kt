package me.pxq.eyepetizer.home.adapters

import me.pxq.common.data.Item
import me.pxq.eyepetizer.home.R

/**
 * Description:
 * Author : pxq
 * Date : 2020/8/4 11:46 PM
 */
object IndexRvHelper {

    //布局类型
    private const val VIEW_HOLDER_TYPE_TEXT_CARD_TITLE = 0
    private const val VIEW_HOLDER_TYPE_TEXT_CARD_TEXT_HEADER5 = 1 + VIEW_HOLDER_TYPE_TEXT_CARD_TITLE
    private const val VIEW_HOLDER_TYPE_FOLLOW_CARD_FOLLOW = 1 + VIEW_HOLDER_TYPE_TEXT_CARD_TEXT_HEADER5
    private const val VIEW_HOLDER_TYPE_INFO_CARD_INFO = 1 + VIEW_HOLDER_TYPE_FOLLOW_CARD_FOLLOW
    private const val VIEW_HOLDER_TYPE_VIDEO_SMALL_CARD = 1 + VIEW_HOLDER_TYPE_INFO_CARD_INFO
    private const val VIEW_HOLDER_TYPE_TEXT_CARD_WITH_TAG = 1 + VIEW_HOLDER_TYPE_VIDEO_SMALL_CARD
    private const val VIEW_HOLDER_TYPE_BANNER = 1 + VIEW_HOLDER_TYPE_TEXT_CARD_WITH_TAG
    private const val VIEW_HOLDER_TYPE_SELECTION_CARD = 1 + VIEW_HOLDER_TYPE_BANNER
    private const val VIEW_HOLDER_TYPE_BRIEF_CARD_TAG = 1 + VIEW_HOLDER_TYPE_SELECTION_CARD
    private const val VIEW_HOLDER_TYPE_BRIEF_CARD_TOP = 1 + VIEW_HOLDER_TYPE_BRIEF_CARD_TAG
    private const val VIEW_HOLDER_TYPE_TEXT_CARD_TEXT_FOOTER2 = 1 + VIEW_HOLDER_TYPE_BRIEF_CARD_TOP
    private const val VIEW_HOLDER_TYPE_HOR_SCROLL_CARD = 1 + VIEW_HOLDER_TYPE_TEXT_CARD_TEXT_FOOTER2
    const val VIEW_HOLDER_TYPE_SPECIAL_SQUARE_CARD = 1 + VIEW_HOLDER_TYPE_HOR_SCROLL_CARD
    private const val VIEW_HOLDER_TYPE_COLUMN_CARD = 1 + VIEW_HOLDER_TYPE_SPECIAL_SQUARE_CARD
    private const val VIEW_HOLDER_TYPE_BANNER_BANNER3 = 1 + VIEW_HOLDER_TYPE_COLUMN_CARD
    private const val VIEW_HOLDER_TYPE_SECTION_AD_SMALL_CARD = 1 + VIEW_HOLDER_TYPE_BANNER_BANNER3
    private const val VIEW_HOLDER_TYPE_SECTION_AD_BIG_CARD = 1 + VIEW_HOLDER_TYPE_SECTION_AD_SMALL_CARD

    // 自动播放广告
    private const val VIEW_HOLDER_TYPE_AUTO_PLAY_VIDEO_AD = 90
    const val VIEW_HOLDER_TYPE_THE_END = 99

    // 没有匹配到
    private const val VIEW_HOLDER_TYPE_NOTHING = 100

    fun getItemViewType(item: Item): Int {
        return when {
            item.type == "theEnd" -> VIEW_HOLDER_TYPE_THE_END
            item.type == "textCard" && item.data.dataType == "TextCardWithRightAndLeftTitle" -> VIEW_HOLDER_TYPE_TEXT_CARD_TITLE
            item.type == "textCard" && item.data.dataType == "TextCard" && "header5" == item.data.type -> VIEW_HOLDER_TYPE_TEXT_CARD_TEXT_HEADER5
            item.type == "textCard" && item.data.dataType == "TextCard" && "footer2" == item.data.type -> VIEW_HOLDER_TYPE_TEXT_CARD_TEXT_FOOTER2
            item.type == "followCard" && item.data.dataType == "FollowCard" -> VIEW_HOLDER_TYPE_FOLLOW_CARD_FOLLOW
            item.type == "informationCard" && item.data.dataType == "InformationCard" -> VIEW_HOLDER_TYPE_INFO_CARD_INFO
            item.type == "videoSmallCard" && item.data.dataType == "VideoBeanForClient" -> VIEW_HOLDER_TYPE_VIDEO_SMALL_CARD
            item.type == "textCard" && item.data.dataType == "TextCardWithTagId" -> VIEW_HOLDER_TYPE_TEXT_CARD_WITH_TAG
            item.type == "banner" && item.data.dataType == "Banner" -> VIEW_HOLDER_TYPE_BANNER
            item.type == "ugcSelectedCardCollection" && item.data.dataType == "ItemCollection" -> VIEW_HOLDER_TYPE_SELECTION_CARD
            item.type == "briefCard" && item.data.dataType == "TagBriefCard" -> VIEW_HOLDER_TYPE_BRIEF_CARD_TAG
            item.type == "briefCard" && item.data.dataType == "TopicBriefCard" -> VIEW_HOLDER_TYPE_BRIEF_CARD_TOP
            item.type == "horizontalScrollCard" && item.data.dataType == "HorizontalScrollCard" -> VIEW_HOLDER_TYPE_HOR_SCROLL_CARD
            item.type == "autoPlayVideoAd" && item.data.dataType == "AutoPlayVideoAdDetail" -> VIEW_HOLDER_TYPE_AUTO_PLAY_VIDEO_AD
            item.type == "specialSquareCardCollection" && item.data.dataType == "ItemCollection" -> VIEW_HOLDER_TYPE_SPECIAL_SQUARE_CARD
            item.type == "columnCardList" && item.data.dataType == "ItemCollection" -> VIEW_HOLDER_TYPE_COLUMN_CARD
            // banner广告
            item.type == "banner3" && item.data.dataType == "Banner" -> VIEW_HOLDER_TYPE_BANNER_BANNER3
            // section ad
            item.type == "sectionAdSmallCard" && item.data.dataType == "SectionAd" -> VIEW_HOLDER_TYPE_SECTION_AD_SMALL_CARD
            item.type == "sectionAdBigCard" && item.data.dataType == "SectionAd" -> VIEW_HOLDER_TYPE_SECTION_AD_BIG_CARD
            else -> VIEW_HOLDER_TYPE_NOTHING
        }
    }

    fun getItemLayout(viewType: Int) = when (viewType) {
        VIEW_HOLDER_TYPE_TEXT_CARD_TITLE -> R.layout.home_rv_item_textcard_rightandleft
        VIEW_HOLDER_TYPE_TEXT_CARD_TEXT_HEADER5 -> R.layout.home_rv_item_textcard_textcard_header5
        VIEW_HOLDER_TYPE_TEXT_CARD_TEXT_FOOTER2 -> R.layout.home_rv_item_textcard_textcard_footer2
        VIEW_HOLDER_TYPE_FOLLOW_CARD_FOLLOW -> R.layout.home_rv_item_followcard_followcard
        VIEW_HOLDER_TYPE_INFO_CARD_INFO -> R.layout.home_rv_item_infocard_infocard
        VIEW_HOLDER_TYPE_VIDEO_SMALL_CARD -> me.pxq.common.R.layout.rv_item_video_small_card
        VIEW_HOLDER_TYPE_TEXT_CARD_WITH_TAG -> R.layout.home_rv_item_textcard_with_tag_id
        VIEW_HOLDER_TYPE_BANNER -> me.pxq.common.R.layout.rv_item_banner
        VIEW_HOLDER_TYPE_SELECTION_CARD -> R.layout.home_rv_item_ugs_selectioncard
        VIEW_HOLDER_TYPE_BRIEF_CARD_TAG -> R.layout.home_rv_item_briefcard_tag
        VIEW_HOLDER_TYPE_BRIEF_CARD_TOP -> R.layout.home_rv_item_briefcard_top
        VIEW_HOLDER_TYPE_HOR_SCROLL_CARD -> me.pxq.common.R.layout.rv_item_hor_scrollcard
        VIEW_HOLDER_TYPE_AUTO_PLAY_VIDEO_AD -> R.layout.home_rv_item_auto_play_video_ad   //视频广告，自动播放
        VIEW_HOLDER_TYPE_SPECIAL_SQUARE_CARD -> R.layout.home_rv_item_special_square_card //热门分类、专题策划共用一个item
        VIEW_HOLDER_TYPE_COLUMN_CARD -> R.layout.home_rv_item_special_square_card
        VIEW_HOLDER_TYPE_BANNER_BANNER3 -> R.layout.home_rv_item_banner3_banner           //banner广告
        VIEW_HOLDER_TYPE_SECTION_AD_SMALL_CARD -> R.layout.home_rv_item_section_ad_small_card   //分段广告 small
        VIEW_HOLDER_TYPE_SECTION_AD_BIG_CARD -> R.layout.home_rv_item_section_ad_big_card  //分段广告 big
        else -> R.layout.home_rv_item_textcard_rightandleft
    }


}