package com.example.food

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.food.db.MealDatabase
import com.example.food.viewmodel.HomeViewModel
import com.example.food.viewmodel.HomeviewmodelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
     val viewmodel:HomeViewModel by lazy {
        val mealDatabase=MealDatabase.getInstance(this)
        val homeviewmodelFactory=HomeviewmodelFactory(mealDatabase)
        ViewModelProvider(this,homeviewmodelFactory)[HomeViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomnavigation=findViewById<BottomNavigationView>(R.id.btm_nav)
        val navController=Navigation.findNavController(this,R.id.host_fragment)

        NavigationUI.setupWithNavController(bottomnavigation,navController)
    }
}