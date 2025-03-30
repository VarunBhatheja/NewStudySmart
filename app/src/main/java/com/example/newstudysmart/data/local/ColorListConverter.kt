package com.example.newstudysmart.data.local

import androidx.room.TypeConverter
import androidx.room.TypeConverters

class ColorListConverter {
// save the data
    @TypeConverter
    fun fromColorList(colorList: List<Int>): String{
        return colorList.joinToString(",") {it.toString()}
    }

// retrieve the data
    @TypeConverter
    fun toColorList(colorListString: String): List<Int> {
        return colorListString.split(",").map{ it.toInt()}
    }
}