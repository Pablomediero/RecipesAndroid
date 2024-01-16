package com.hiberus.receptom.data.remote

import com.google.gson.Gson
import com.hiberus.receptom.data.remote.mapper.ResponseMapper
import com.hiberus.receptom.data.remote.service.ChatGPTService
import com.hiberus.receptom.model.local.Order
import com.hiberus.receptom.model.local.Recipe

class IALocalImpl(
    private val chatGPTService: ChatGPTService,
    private val responseMapper: ResponseMapper,
    private val gson: Gson
) {

    suspend fun sendPostMessage(order: Order): Recipe {
        return if(order.mode == 1){
            val generatedAnswer = responseMapper.mapFromResponse(chatGPTService.getPrompt(responseMapper.mapToResponseM1(order.ingredients)))
            parseJson(generatedAnswer.choices[0].text)
        }else{
            val generatedAnswer =responseMapper.mapFromResponse(chatGPTService.getPrompt(responseMapper.mapToResponseM2(order.ingredients)))
            parseJson(generatedAnswer.choices[0].text.substring(1))
        }
    }
    private fun parseJson(generatedAnswer: String) = gson.fromJson(generatedAnswer, Recipe::class.java)

}