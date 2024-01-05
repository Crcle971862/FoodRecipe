package com.example.food.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.food.db.MealDatabase
import com.example.food.pojo.Meal
import com.example.food.pojo.MealList
import com.example.food.retrofit.Mealapi
import com.example.food.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

public class MealViewModel(val mealDatabase:MealDatabase
):ViewModel() {
    private var MealDetailsLiveData=MutableLiveData<Meal>()

    fun getMealDetails(id:String){
       val api=RetrofitInstance.getInstance().create(Mealapi::class.java)
        api.getmealdetails(id).enqueue(object :Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if(response!=null){
                    MealDetailsLiveData.value=response.body()!!.meals[0]
                }
                else return
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun observeMealDetailLiveData():LiveData<Meal>{
        return MealDetailsLiveData
    }

    fun insertmeal(meal: Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().upsert(meal)
        }
    }
}