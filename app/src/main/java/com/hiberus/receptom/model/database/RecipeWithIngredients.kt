package com.hiberus.receptom.model.database

import androidx.room.Embedded
import androidx.room.Relation

data class RecipeWithIngredients(
    @Embedded val recipe: CachedRecipe,
    @Relation(
        parentColumn = "id",
        entityColumn = "idRecipe"
    )
    val ingrediens : List<CachedIngredient>
)
