package com.example.food.bottomsheet

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.food.Home_Fragment
import com.example.food.MainActivity
import com.example.food.Meal_Activity
import com.example.food.R
import com.example.food.databinding.FragmentMealBottomSheetBinding
import com.example.food.viewmodel.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


private const val meal_ID = "param1"


class MealBottomSheet_Fragment : BottomSheetDialogFragment() {
    private var mealid: String? = null
    private lateinit var binding:FragmentMealBottomSheetBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var img:String
    private lateinit var name:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mealid = it.getString(meal_ID)
        }
        viewModel=(activity as MainActivity).viewmodel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentMealBottomSheetBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mealid?.let {
            viewModel.getmealbyID(it) }

        observebottomsheetmeal()
        bottomsheetClick()
    }

    private fun bottomsheetClick() {
        binding.bottomSheet.setOnClickListener {
            val intent=Intent(requireContext(),Meal_Activity::class.java)
            intent.putExtra(Home_Fragment.mealid,mealid)
            intent.putExtra(Home_Fragment.mealthumb,img)
            intent.putExtra(Home_Fragment.mealname,name)
            startActivity(intent)
        }
    }

    private fun observebottomsheetmeal() {
        viewModel.observebottomsheetmeal().observe(viewLifecycleOwner, Observer {
            Glide.with(this).load(it.strMealThumb).into(binding.imgCategory)
            binding.tvMealCountry.text=it.strArea
            binding.tvMealCategory.text=it.strCategory
            binding.tvMealNameInBtmsheet.text=it.strMeal
            img= it.strMealThumb!!
            name= it.strMeal!!
        })
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String) =
            MealBottomSheet_Fragment().apply {
                arguments = Bundle().apply {
                    putString(meal_ID, param1)
                }
            }
    }
}