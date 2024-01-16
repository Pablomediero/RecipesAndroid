package com.hiberus.receptom.model.local

data class Order (
    val ingredients: List<Ingredient>,
    val mode: Int
)