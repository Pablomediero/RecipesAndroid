package com.hiberus.receptom.data.database

import com.hiberus.receptom.domain.repository.RecipeRepository
import com.hiberus.receptom.model.local.Recipe

class RecipeDataImpl(
    private val recipeLocalImpl: RecipeLocalImpl
) : RecipeRepository {
    override fun getAllRecipe(): List<Recipe> = recipeLocalImpl.getAllRecipes()
    override fun getRecipe(recipeId: Int): Recipe = recipeLocalImpl.getRecipe(recipeId)
    override fun addRecipe(recipe: Recipe) = recipeLocalImpl.addRecipe(recipe)
    override fun deleteRecipe(recipe: Recipe) = recipeLocalImpl.deleteRecipe(recipe)
}