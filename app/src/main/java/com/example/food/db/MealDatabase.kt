package com.example.food.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.food.pojo.Meal

@Database(entities = [Meal::class], version = 1)
@TypeConverters(MealtypeConverter::class)
abstract class MealDatabase:RoomDatabase() {

    abstract fun mealDao():MealDao

    companion object{
        @Volatile
        var Instance:MealDatabase?=null

        @Synchronized
        fun getInstance(context: Context):MealDatabase{
            if (Instance==null){
                Instance=Room.databaseBuilder(context,MealDatabase::class.java,"meal.db").fallbackToDestructiveMigration().build()
            }
            return Instance as MealDatabase
        }
    }

}