package com.hiberus.receptom.model.remote

import com.google.gson.annotations.SerializedName

data class RequestBody(
    @SerializedName("model")
    val model: String,

    @SerializedName("prompt")
    val prompt: String,

    @SerializedName("max_tokens")
    val maxTokens: Int,

    @SerializedName("temperature")
    var temperature: Int,

)