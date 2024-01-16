package com.hiberus.receptom.data.database
import com.hiberus.receptom.data.database.mapper.CacheMapper
import com.hiberus.receptom.data.database.service.AppDatabase
import com.hiberus.receptom.model.database.CachedIngredient
import com.hiberus.receptom.model.database.RecipeWithIngredients
import com.hiberus.receptom.model.local.Recipe
class RecipeLocalImpl(
    private val appDatabase: AppDatabase,
    private val cachedMapper: CacheMapper,
) {
    fun getAllRecipes(): List<Recipe> {
        val cachedRecipes: List<RecipeWithIngredients> = appDatabase.recipeDao().getRecipesWithIngredients()
        val recipes: MutableList<Recipe> = mutableListOf()
        cachedRecipes.forEach { recipes.add(cachedMapper.mapFromCache(it)) }
        return recipes
    }

    fun getRecipe(recipeId: Int): Recipe =
        cachedMapper.mapFromCache(appDatabase.recipeDao().getRecipeWithIngredients(recipeId.toLong()))

    fun addRecipe(recipe: Recipe) {
        val cachedIngredients: MutableList<CachedIngredient> = arrayListOf()
        recipe.ingredients.forEach{cachedIngredients.add(cachedMapper.mapToCache(recipe,it))}
        appDatabase.recipeDao().insertRecipeWithIngredients(cachedMapper.mapToCache(recipe), cachedIngredients)

    }

    fun deleteRecipe(recipe: Recipe) {
        appDatabase.recipeDao().deleteIngredient(recipe.id)
        appDatabase.recipeDao().deleteRecipe(cachedMapper.mapToCache(recipe))
    }
}





