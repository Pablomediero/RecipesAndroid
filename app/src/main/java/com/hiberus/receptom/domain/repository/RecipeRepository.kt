package com.hiberus.receptom.domain.repository

import com.hiberus.receptom.model.local.Recipe

interface RecipeRepository {

    fun getAllRecipe(): List<Recipe>
    fun getRecipe(recipeId: Int): Recipe
    fun addRecipe(recipe: Recipe)
    fun deleteRecipe(recipe: Recipe)
}