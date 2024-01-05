package com.example.food.retrofit

import com.example.food.pojo.CategoryList
import com.example.food.pojo.Meal
import com.example.food.pojo.MealsByCategoryList
import com.example.food.pojo.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Mealapi {

    @GET("random.php")
    fun getRandomeMeal(): Call<MealList>

    @GET("lookup.php")
    fun getmealdetails(@Query("i") id:String):Call<MealList>

    @GET("filter.php?")
    fun getPopularitems(@Query("c")categoryname:String):Call<MealsByCategoryList>

    @GET("categories.php")
    fun getCategories():Call<CategoryList>

    @GET("filter.php")
    fun getmealsbycategory(@Query("c")categoryname: String):Call<MealsByCategoryList>

    @GET("search.php")
    fun searchmeal(@Query("s")searchQuery:String):Call<MealList>
}