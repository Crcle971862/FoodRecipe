package com.example.food.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.food.Home_Fragment
import com.example.food.Meal_Activity
import com.example.food.databinding.MealsItemBinding
import com.example.food.pojo.MealsByCategory

class CategorymealAdapter(val context: Context): RecyclerView.Adapter<CategorymealAdapter.Viewholder>() {
    private var mealslist=ArrayList<MealsByCategory>()
    fun setmealslist(mealslist:List<MealsByCategory>){
        this.mealslist= mealslist as ArrayList<MealsByCategory>
        notifyDataSetChanged()
    }
    class Viewholder(val binding:MealsItemBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategorymealAdapter.Viewholder {
        return Viewholder(MealsItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: CategorymealAdapter.Viewholder, position: Int) {
        Glide.with(holder.itemView).load(mealslist[position].strMealThumb).into(holder.binding.imgMeal)
        holder.binding.tvMealName.text=mealslist[position].strMeal
        holder.itemView.setOnClickListener {
            val intent=Intent(context,Meal_Activity::class.java)
            intent.putExtra(Home_Fragment.mealname,mealslist[position].strMeal)
            intent.putExtra(Home_Fragment.mealthumb,mealslist[position].strMealThumb)
            intent.putExtra(Home_Fragment.mealid,mealslist[position].idMeal)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return mealslist.size
    }
}