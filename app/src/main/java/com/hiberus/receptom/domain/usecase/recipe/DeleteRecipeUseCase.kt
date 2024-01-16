package com.hiberus.receptom.domain.usecase.recipe

import com.hiberus.receptom.domain.repository.RecipeRepository
import com.hiberus.receptom.model.local.Recipe

class DeleteRecipeUseCase (
    private val recipeRepository: RecipeRepository
) {
    fun execute(recipe: Recipe){
        recipeRepository.deleteRecipe(recipe)
    }
}