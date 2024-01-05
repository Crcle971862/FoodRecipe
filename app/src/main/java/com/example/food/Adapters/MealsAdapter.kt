package com.example.food.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.food.Adapters.MealsAdapter.viewholder
import com.example.food.Home_Fragment
import com.example.food.Meal_Activity
import com.example.food.databinding.MealsItemBinding
import com.example.food.pojo.Meal

class MealsAdapter(val context: Context): RecyclerView.Adapter<viewholder>() {
    class viewholder(val binding: MealsItemBinding):RecyclerView.ViewHolder(binding.root) {
    }
    private val diffUtil=object : DiffUtil.ItemCallback<Meal>(){
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal==newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem==newItem
        }
    }
    val differ= AsyncListDiffer(this,diffUtil)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): viewholder {
        return viewholder(MealsItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        val meal=differ.currentList[position]
        Glide.with(holder.itemView).load(meal.strMealThumb).into(holder.binding.imgMeal)
        holder.binding.tvMealName.text=meal.strMeal
        holder.itemView.setOnClickListener {
            val intent=Intent(context,Meal_Activity::class.java)
            intent.putExtra(Home_Fragment.mealid,meal.idMeal)
            intent.putExtra(Home_Fragment.mealthumb,meal.strMealThumb)
            intent.putExtra(Home_Fragment.mealname,meal.strMeal)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}