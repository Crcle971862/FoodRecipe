package com.example.food.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.food.pojo.MealsByCategory
import com.example.food.pojo.MealsByCategoryList
import com.example.food.retrofit.Mealapi
import com.example.food.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Categoryviewmodel: ViewModel() {
    val mealslivedata=MutableLiveData<List<MealsByCategory>>()
    fun getMealbycategory(categoryname:String){
        val api= RetrofitInstance.getInstance().create(Mealapi::class.java)
        api.getmealsbycategory(categoryname).enqueue(object :Callback<MealsByCategoryList>{
            override fun onResponse(call: Call<MealsByCategoryList>, response: Response<MealsByCategoryList>) {
                mealslivedata.value= response.body()!!.meals
            }

            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
    fun observemealslivedata():LiveData<List<MealsByCategory>>{
        return mealslivedata
    }
}