package com.example.food


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.food.Adapters.CategoriesAdapter
import com.example.food.Adapters.MostPopularAdapter
import com.example.food.bottomsheet.MealBottomSheet_Fragment
import com.example.food.databinding.FragmentHomeBinding
import com.example.food.pojo.MealsByCategory
import com.example.food.pojo.Meal
import com.example.food.viewmodel.HomeViewModel


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class Home_Fragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewmodel:HomeViewModel
    private lateinit var randommeal:Meal
    private lateinit var popularAdapter:MostPopularAdapter
    private lateinit var rv:RecyclerView
    private lateinit var categoriesadapter:CategoriesAdapter

    companion object{
        const val mealid="mealid"
        const val mealname="mealname"
        const val mealthumb="mealthumb"
    }

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            //Homemvvm=androidx.lifecycle.ViewModelProvider(this).get(HomeViewModel::class.java)
        viewmodel=(activity as MainActivity).viewmodel
        popularAdapter= MostPopularAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel.getRandomMeal()
        observeRandomMeal()
        rv=view.findViewById(R.id.rec_view_meals_popular)
        binding.randomMeal.setOnClickListener {
            val intent=Intent(activity,Meal_Activity::class.java)
            intent.putExtra(mealid,randommeal.idMeal)
            intent.putExtra(mealname,randommeal.strMeal)
            intent.putExtra(mealthumb,randommeal.strMealThumb)
            startActivity(intent)
        }
        viewmodel.GetPopularitems()
        observePopularitemsliveData()
        popularRecyclerview()

        categoriesRecyclerview()
        viewmodel.getCategories()
        observecategoriesliveData()
        onCategoryclick()
        onPopularlongitemclick()
    }

    private fun onPopularlongitemclick() {
        popularAdapter.onItemLongClickListener={
            val mealbottomsheetFragment=MealBottomSheet_Fragment.newInstance(it.idMeal)
            mealbottomsheetFragment.show(childFragmentManager,"Meal info")
        }
    }

    private fun onCategoryclick() {
        categoriesadapter.onItemClick={it->
            val intent=Intent(context,CategoryMeals_Activity::class.java)
            intent.putExtra("category name",it.strCategory)
            startActivity(intent)
        }
    }

    private fun categoriesRecyclerview() {
        categoriesadapter=CategoriesAdapter()
        binding.recyclerView.apply {
            layoutManager=GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter=categoriesadapter
        }
    }

    private fun observecategoriesliveData() {
        viewmodel.observelivecategoriesdata().observe(viewLifecycleOwner,{
            categoriesadapter.setCategoryList(it)
        })
    }

    private fun popularRecyclerview() {
        binding.recViewMealsPopular.apply {
            layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            //rv.layoutManager=layoutManager
            adapter=popularAdapter
            //rv.adapter=adapter
        }
    }

    private fun observePopularitemsliveData() {
        viewmodel.observepopularitemlivedata().observe(viewLifecycleOwner,{ mealList->popularAdapter.setMeals(mealsList =mealList as ArrayList<MealsByCategory>)}
        )
//        Homemvvm.observepopularitemlivedata().observe(viewLifecycleOwner,object :Observer<CategoryMeals>{
//            override fun onChanged(value: CategoryMeals) {
//                popularAdapter.setMeals(mealsList = ArrayList<value!!>)
//            }
//        })
    }

    private fun observeRandomMeal() {
        viewmodel.livedata().observe(viewLifecycleOwner,object :Observer<Meal>{
            override fun onChanged(value: Meal) {
                Glide.with(this@Home_Fragment).load(value!!.strMealThumb).into(binding.imgRandomMeal)
                randommeal=value
            }
        })
    }
}