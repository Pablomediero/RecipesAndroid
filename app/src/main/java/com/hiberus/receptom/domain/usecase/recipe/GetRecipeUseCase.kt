package com.hiberus.receptom.domain.usecase.recipe

import com.hiberus.receptom.domain.repository.RecipeRepository
import com.hiberus.receptom.model.local.Recipe

class GetRecipeUseCase (
    private val recipeRepository: RecipeRepository
) {
    fun execute(recipeId: Int): Recipe {
        return recipeRepository.getRecipe(recipeId)
    }
}