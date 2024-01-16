package com.hiberus.receptom.data.database.mapper

import com.hiberus.receptom.model.database.CachedIngredient
import com.hiberus.receptom.model.database.CachedRecipe
import com.hiberus.receptom.model.database.RecipeWithIngredients
import com.hiberus.receptom.model.local.Ingredient
import com.hiberus.receptom.model.local.Recipe

class CacheMapper {

    fun mapToCache(type: Recipe): CachedRecipe {
        return CachedRecipe(
            type.id,
            type.name,
            type.instructions,
            type.serving
        )
    }


    fun mapToCache(recipe: Recipe, type: Ingredient): CachedIngredient {
        return CachedIngredient(
            type.id,
            recipe.id,
            type.name
        )
    }

    fun mapFromCache(type: RecipeWithIngredients): Recipe {
        return Recipe(
            type.recipe.id,
            type.recipe.name,
            type.ingrediens.map {
                mapFromCache(it)
            },
            type.recipe.instructions,
            type.recipe.serving
        )
    }

    fun mapFromCache(type: CachedIngredient): Ingredient {
        return Ingredient(
            type.id,
            type.idRecipe,
            type.name
        )
    }
}