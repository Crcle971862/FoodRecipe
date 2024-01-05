package com.example.food.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.food.databinding.PopularItemsBinding
import com.example.food.pojo.MealsByCategory

class MostPopularAdapter():RecyclerView.Adapter<MostPopularAdapter.ViewHolder>() {
    private var mealsList=ArrayList<MealsByCategory>()
    var onItemLongClickListener:((MealsByCategory)->Unit)?=null

    fun setMeals(mealsList:ArrayList<MealsByCategory>){
        this.mealsList=mealsList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.popular_items,parent,false))
        return ViewHolder(PopularItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView).load(mealsList[position].strMealThumb).into(holder.binding.imgPopularMeal)
        holder.itemView.setOnClickListener {
            onItemLongClickListener?.invoke(mealsList[position])
            true
        }
    }
//    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
//        val Popularmeal=itemView.findViewById<ImageView>(R.id.img_popular_meal)
//    }
    class ViewHolder(var binding:PopularItemsBinding):RecyclerView.ViewHolder(binding.root)

}