package com.hiberus.receptom.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.hiberus.receptom.model.database.CachedIngredient
import com.hiberus.receptom.model.database.CachedRecipe
import com.hiberus.receptom.model.database.RecipeWithIngredients

@Dao
interface RecipeDao {
    @Transaction
    @Query("SELECT * FROM recipes")
    fun getRecipesWithIngredients(): List<RecipeWithIngredients>
    @Transaction
    @Query("SELECT * FROM recipes WHERE recipes.id = :recipeId")
    fun getRecipeWithIngredients(recipeId: Long): RecipeWithIngredients
    @Insert()
    fun addRecipe(recipe: CachedRecipe) : Long
    @Insert()
    fun addIngredient(ingredient: List<CachedIngredient>)
    @Transaction
    @Insert
    fun insertRecipeWithIngredients(recipe: CachedRecipe, ingredients: List<CachedIngredient>){
        val recipeIns = addRecipe(recipe)
        ingredients.forEach { it.idRecipe = recipeIns }
        addIngredient(ingredients)
    }
    @Delete()
    fun deleteRecipe(recipe: CachedRecipe)
    @Query("DELETE FROM ingredient WHERE idRecipe = :recipeId")
    fun deleteIngredient(recipeId: Long)


}