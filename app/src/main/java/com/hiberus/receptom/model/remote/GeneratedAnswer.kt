package com.hiberus.receptom.model.remote

import com.google.gson.annotations.SerializedName

data class GeneratedAnswer(
    @SerializedName("choices")
    val choices: List<Choice>,

    @SerializedName("created")
    val created: Int,

    @SerializedName("id")
    val id: String,

    @SerializedName("model")
    val model: String,

    @SerializedName("object")
    val `object`: String,

    @SerializedName("usage")
    val usage: Usage
)