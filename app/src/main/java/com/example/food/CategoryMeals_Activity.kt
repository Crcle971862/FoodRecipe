package com.example.food

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food.Adapters.CategorymealAdapter
import com.example.food.databinding.ActivityCategoryMealsBinding
import com.example.food.databinding.PopularItemsBinding
import com.example.food.viewmodel.Categoryviewmodel

class CategoryMeals_Activity : AppCompatActivity() {
    lateinit var binding: ActivityCategoryMealsBinding
    lateinit var categoryMvvm:Categoryviewmodel
    lateinit var categorymealAadapter:CategorymealAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prepareRecyclerview()
        categoryMvvm=ViewModelProvider(this).get(Categoryviewmodel::class.java)
        categoryMvvm.getMealbycategory(intent.getStringExtra("category name")!!)
        categoryMvvm.observemealslivedata().observe(this,{it->
            categorymealAadapter.setmealslist(it)
        })

    }

    private fun prepareRecyclerview() {
        categorymealAadapter= CategorymealAdapter(this)
        binding.mealRecyclerview.apply {
            layoutManager=GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter=categorymealAadapter

        }
    }
}