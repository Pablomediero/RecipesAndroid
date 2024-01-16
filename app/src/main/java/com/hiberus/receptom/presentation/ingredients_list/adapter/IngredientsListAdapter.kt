package com.hiberus.receptom.presentation.ingredients_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.hiberus.receptom.databinding.ItemIngredientBinding
import com.hiberus.receptom.model.local.Ingredient

class IngredientsListAdapter : ListAdapter<Ingredient, IngredientsListAdapter.IngredientsViewHolder>(
    object : DiffUtil.ItemCallback<Ingredient>() {
        override fun areItemsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
            return oldItem.name == newItem.name
        }
    }
) {
    var onIconClicked : (Int) -> Unit = {}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder {
        val itemBinding =
            ItemIngredientBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IngredientsViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        val item = getItem(position)
        holder.ingredientName.text = item.name
        holder.ingredientIconClose.setOnClickListener{
            onIconClicked(position)
        }
    }

    inner class IngredientsViewHolder(itemBinding: ItemIngredientBinding) :
        ViewHolder(itemBinding.root) {
        val ingredientName: TextView = itemBinding.tvItemIngredientName
        val ingredientIconClose: ImageView = itemBinding.ivItemIngredientRemoveIcon
    }
}