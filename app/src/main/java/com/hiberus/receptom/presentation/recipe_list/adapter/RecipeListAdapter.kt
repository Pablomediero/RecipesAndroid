package com.hiberus.receptom.presentation.recipe_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.hiberus.receptom.databinding.ItemRecipeBinding
import com.hiberus.receptom.model.local.Recipe


class RecipeListAdapter : ListAdapter<Recipe, RecipeListAdapter.RecipeViewHolder>(
    object : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem.id == newItem.id
        }
    }
) {
    var onClickListener: (Recipe) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val itemBinding =
            ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener(item as Recipe)
        }
        holder.recipeName.text = item.name
    }

    inner class RecipeViewHolder(itemBinding: ItemRecipeBinding) : ViewHolder(itemBinding.root) {
        val recipeName: TextView = itemBinding.tvItemRecipeName
    }
}