package com.example.food.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.food.db.MealDatabase
import com.example.food.pojo.Category
import com.example.food.pojo.CategoryList
import com.example.food.pojo.MealsByCategoryList
import com.example.food.pojo.MealsByCategory
import com.example.food.pojo.Meal
import com.example.food.pojo.MealList
import com.example.food.retrofit.Mealapi
import com.example.food.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class HomeViewModel(val mealDatabase: MealDatabase):ViewModel() {
    private var randomMealLiveData=MutableLiveData<Meal>()
    private var popularitems=MutableLiveData<List<MealsByCategory>>()
    private var categoriesLiveData=MutableLiveData<List<Category>>()
    private var favoritesmeallivedata=mealDatabase.mealDao().getallmeal()
    private var bottomsheetMutableLiveData=MutableLiveData<Meal>()
    private var api=RetrofitInstance.getInstance().create(Mealapi::class.java)
    private val searchmealLiveData= MutableLiveData<List<Meal>>()
    fun getRandomMeal(){
        //val api= RetrofitInstance.getInstance().create(Mealapi::class.java)
        api.getRandomeMeal().enqueue(object :retrofit2.Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>){
                val res: Meal =response.body()!!.meals[0]
                randomMealLiveData.value=res
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun getmealbyID(id:String){
        //val api=RetrofitInstance.getInstance().create(Mealapi::class.java)
        api.getmealdetails(id).enqueue(object :Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                val meal=response.body()?.meals?.first()
                meal?.let {
                    bottomsheetMutableLiveData.postValue(it)
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun GetPopularitems(){
        //val api= RetrofitInstance.getInstance().create(Mealapi::class.java)
        api.getPopularitems("seafood").enqueue(object :Callback<MealsByCategoryList>{
            override fun onResponse(call: Call<MealsByCategoryList>, response: Response<MealsByCategoryList>) {
                popularitems.value= response.body()!!.meals
            }

            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun getCategories(){
        //val api= RetrofitInstance.getInstance().create(Mealapi::class.java)
        api.getCategories().enqueue(object :Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                categoriesLiveData.value=response.body()!!.categories
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun searchmeals(searchquery:String){
        api.searchmeal(searchquery).enqueue(object :Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                val mealList= response.body()?.meals
                mealList.let {
                    searchmealLiveData.postValue(it)
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.e("Homeviewmodel",t.message.toString())
            }
        })
    }

    fun observesearchmeallivedata():LiveData<List<Meal>> = searchmealLiveData

    fun livedata():LiveData<Meal>{
        return randomMealLiveData
    }

    fun delete(meal: Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().delete(meal)
        }
    }
    fun insertmeal(meal: Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().upsert(meal)
        }
    }

    fun observepopularitemlivedata():LiveData<List<MealsByCategory>>{
        return popularitems
    }



    fun observelivecategoriesdata():LiveData<List<Category>>{
        return categoriesLiveData
    }

    fun observefavoritemealslivedata():LiveData<List<Meal>>{
        return favoritesmeallivedata
    }

    fun observebottomsheetmeal():LiveData<Meal>{
        return bottomsheetMutableLiveData
    }
}