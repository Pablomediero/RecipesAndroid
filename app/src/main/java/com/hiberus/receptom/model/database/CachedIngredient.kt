package com.hiberus.receptom.model.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingredient")
data class CachedIngredient(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo("idRecipe") var idRecipe: Long,
    @ColumnInfo("name") val name: String,
)
