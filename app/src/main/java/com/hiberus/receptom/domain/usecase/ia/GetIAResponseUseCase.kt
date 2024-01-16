package com.hiberus.receptom.domain.usecase.ia

import com.hiberus.receptom.domain.repository.IARepository
import com.hiberus.receptom.model.local.Order
import com.hiberus.receptom.model.local.Recipe

class GetIAResponseUseCase (private val iaRepository: IARepository){
     suspend fun execute(order: Order): Recipe {
        return iaRepository.sendPostMessage(order)
    }
}