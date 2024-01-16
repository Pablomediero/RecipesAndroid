package com.hiberus.receptom.data.database.service

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hiberus.receptom.data.database.dao.RecipeDao
import com.hiberus.receptom.model.database.CachedIngredient
import com.hiberus.receptom.model.database.CachedRecipe

@Database(
    entities = [CachedRecipe::class, CachedIngredient::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao() : RecipeDao
}