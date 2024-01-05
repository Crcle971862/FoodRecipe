package com.example.food

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food.Adapters.CategoriesAdapter
import com.example.food.Adapters.MealsAdapter
import com.example.food.databinding.FragmentSearchBinding
import com.example.food.viewmodel.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class Search_Fragment : Fragment() {
    private lateinit var binding:FragmentSearchBinding
    private lateinit var viewmodel:HomeViewModel
    private lateinit var searchRecyclerviewAdapter: MealsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewmodel=(activity as MainActivity).viewmodel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecyclerview()
        observesearchmealslivedata()
        binding.searchImg.setOnClickListener { searchmeals() }

        var searchjob: Job? =null
        binding.edSearch.addTextChangedListener {
            searchjob?.cancel()
            searchjob= lifecycleScope.launch {
                viewmodel.searchmeals(it.toString())
            }
        }
    }

    private fun observesearchmealslivedata() {
        viewmodel.observesearchmeallivedata().observe(viewLifecycleOwner, Observer{
            searchRecyclerviewAdapter.differ.submitList(it)
        })
    }

    private fun searchmeals() {
        val searchquery=binding.edSearch.text.toString()
        if (searchquery.isNotEmpty()){
            viewmodel.searchmeals(searchquery)
        }
    }

    private fun prepareRecyclerview() {
        searchRecyclerviewAdapter= MealsAdapter(requireContext())
        binding.rvSearchMeal.apply {
            layoutManager=GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter=searchRecyclerviewAdapter
        }
    }

}