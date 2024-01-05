package com.example.food

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.food.databinding.ActivityMealBinding
import com.example.food.db.MealDatabase
import com.example.food.pojo.Meal
import com.example.food.viewmodel.MealViewModel
import com.example.food.viewmodel.MealviewmodelFactory
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Meal_Activity : AppCompatActivity() {
    private lateinit var binding:ActivityMealBinding
    private lateinit var mealid:String
    private lateinit var mealname:String
    private lateinit var mealthumb:String
    private lateinit var img:ImageView
    private lateinit var name:CollapsingToolbarLayout
    private lateinit var mealmvvm:MealViewModel
    private lateinit var Categoryinfo:TextView
    private lateinit var Areainfo:TextView
    private lateinit var Instructions:TextView
    private lateinit var savemealbtn:FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMealBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_meal)
        img=findViewById(R.id.img_meal)
        name=findViewById(R.id.collapsing_toolbar)
        Categoryinfo=findViewById<TextView>(R.id.tv_categoryInfo)
        Areainfo=findViewById<TextView>(R.id.tv_areaInfo)
        Instructions=findViewById(R.id.tv_content)
        savemealbtn=findViewById(R.id.btn_save)
        getrandommealInformation()
        setinformation()

        val mealDatabase=MealDatabase.getInstance(this)
        val viewModelFactory=MealviewmodelFactory(mealDatabase)
        mealmvvm= ViewModelProvider(this,viewModelFactory)[MealViewModel::class.java]
        //mealmvvm=ViewModelProvider(this).get(MealViewModel::class.java)

        mealmvvm.getMealDetails(mealid)
        observemeallivedata()
        onFavouriteClick()
    }

    private fun onFavouriteClick() {
        savemealbtn.setOnClickListener {
            try {
                mealtoSave?.let {
                    mealmvvm.insertmeal(it)
                }
            }
            catch (e:ArithmeticException){
                println(e)
            }

        }
    }

    private var mealtoSave:Meal?=null
    private fun observemeallivedata() {
        mealmvvm.observeMealDetailLiveData().observe(this,object :Observer<Meal>{
            override fun onChanged(value: Meal) {
                Categoryinfo.text="Category : "+value.strCategory
                Areainfo.text="Area : "+value.strArea
                Instructions.text=value.strInstructions
                mealtoSave=value
            }

        })
    }

    private fun setinformation() {
        Glide.with(applicationContext).load(mealthumb).into(img)
        name.title=mealname
    }

    private fun getrandommealInformation() {
        val intent=intent
        mealid=intent.getStringExtra(Home_Fragment.mealid).toString()
        mealname=intent.getStringExtra(Home_Fragment.mealname).toString()
        mealthumb=intent.getStringExtra(Home_Fragment.mealthumb).toString()
        Log.d("manish",mealthumb)
    }
}