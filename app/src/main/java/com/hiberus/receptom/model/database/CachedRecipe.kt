package com.hiberus.receptom.model.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class CachedRecipe(
    @PrimaryKey(autoGenerate = true) var id: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "instructions") val instructions: String,
    @ColumnInfo(name = "serving") val serving: Int
)
