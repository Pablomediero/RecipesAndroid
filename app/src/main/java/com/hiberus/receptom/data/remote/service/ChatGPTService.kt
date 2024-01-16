package com.hiberus.receptom.data.remote.service

import com.hiberus.receptom.model.remote.GeneratedAnswer
import com.hiberus.receptom.model.remote.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ChatGPTService {
    @POST("completions")
    suspend fun getPrompt(@Body body: RequestBody) : Response<GeneratedAnswer>
}