package com.hiberus.receptom.model.remote

import com.google.gson.annotations.SerializedName

data class Usage(

    @SerializedName("completion_tokens")
    val completion_tokens: Int,

    @SerializedName("prompt_tokens")
    val prompt_tokens: Int,

    @SerializedName("total_tokens")
    val total_tokens: Int

)