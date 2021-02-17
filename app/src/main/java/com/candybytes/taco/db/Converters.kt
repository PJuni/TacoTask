package com.candybytes.taco.db

import androidx.room.TypeConverter
import com.candybytes.taco.ui.extensions.fromJson
import com.candybytes.taco.ui.extensions.toJson
import com.candybytes.taco.vo.Food
import com.candybytes.taco.vo.Nutrient
import com.google.gson.Gson


class Converters {
    @TypeConverter
    fun fromHM(map: HashMap<String, Nutrient>?) = map?.toJson()

    @TypeConverter
    fun toHM(map: String?) = map.fromJson<HashMap<String, Nutrient>?>()

    @TypeConverter
    fun listToJson(value: List<Food>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<Food>::class.java).toList()
}
