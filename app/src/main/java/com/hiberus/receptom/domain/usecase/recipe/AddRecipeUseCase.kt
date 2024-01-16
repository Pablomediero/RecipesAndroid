package com.hiberus.receptom.domain.usecase.recipe

import com.hiberus.receptom.domain.repository.RecipeRepository
import com.hiberus.receptom.model.local.Recipe

class AddRecipeUseCase (
    private val recipeRepository: RecipeRepository
) {
    fun execute(recipe: Recipe){
        recipeRepository.addRecipe(recipe)
    }
}