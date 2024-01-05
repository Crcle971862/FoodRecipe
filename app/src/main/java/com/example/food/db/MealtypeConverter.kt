package com.example.food.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters

@TypeConverters
class MealtypeConverter {
    @TypeConverter
    fun fromAnytoString(attribute:Any?):String{
        if (attribute==null){
            return ""
        }
        return attribute as String
    }

    @TypeConverter
    fun fromStringtoAny(attribute: String?):Any{
        if (attribute==null){
            return ""
        }

        return attribute
    }
}