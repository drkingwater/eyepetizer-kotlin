package me.pxq.framework.db.converter

import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import me.pxq.framework.model.Item
import me.pxq.utils.JsonUtil

/**
 * Description:
 * Author : pxq
 * Date : 2020/7/16 10:26 PM
 */
class HomeItemConverter {

    @TypeConverter
    fun itemListToString(items: List<Item>):String {
        return JsonUtil.toJson(items)
    }

    @TypeConverter
    fun stringToItemList(json : String) : List<Item> {
        val itemsType = object : TypeToken<List<Item>>(){}.type
        return JsonUtil.fromGson(json, itemsType)
    }

}