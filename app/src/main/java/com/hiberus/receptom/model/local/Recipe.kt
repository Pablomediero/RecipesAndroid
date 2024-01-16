package com.hiberus.receptom.model.local


data class Recipe(
    var id: Long = 0,
    val name: String,
    val ingredients: List<Ingredient>,
    val instructions: String,
    val serving: Int,
)
