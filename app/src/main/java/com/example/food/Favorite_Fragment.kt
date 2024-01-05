package com.example.food

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.food.Adapters.MealsAdapter
import com.example.food.databinding.FragmentFavoriteBinding
import com.example.food.viewmodel.HomeViewModel
import com.google.android.material.snackbar.Snackbar

class Favorite_Fragment() : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var mealsAdapter: MealsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=(activity as MainActivity).viewmodel
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(inflater)
        return binding.root
    }
    //callback: ItemTouchHelper.Callback
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecyclerview()
        observefavorite()
        val itemsTouchHelper=object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN
            ,ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            )=true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position=viewHolder.adapterPosition
                viewModel.delete(mealsAdapter.differ.currentList[position])
                Snackbar.make(requireView(),"Meal Deleted",Snackbar.LENGTH_LONG).show()
//                Snackbar.make(requireView(),"Meal deleted",Snackbar.LENGTH_LONG).setAction("Undo",View.OnClickListener {
//                    viewModel.insertmeal(favoriteMealsAdapter.differ.currentList[position])
//                }).show()
            }
        }
        ItemTouchHelper(itemsTouchHelper).attachToRecyclerView(binding.favoriteRv)
    }

    private fun prepareRecyclerview() {
        mealsAdapter= MealsAdapter(requireContext())
        binding.favoriteRv.apply {
            layoutManager=GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter=mealsAdapter
        }
    }

    private fun observefavorite() {
        viewModel.observefavoritemealslivedata().observe(viewLifecycleOwner, Observer { meals->
            meals.forEach{
                Log.d("manish",it.idMeal)
                mealsAdapter.differ.submitList(meals)
            }
        })
    }
}