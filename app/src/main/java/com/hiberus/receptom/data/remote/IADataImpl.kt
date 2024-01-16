package com.hiberus.receptom.data.remote

import com.hiberus.receptom.domain.repository.IARepository
import com.hiberus.receptom.model.local.Order
import com.hiberus.receptom.model.local.Recipe

class IADataImpl(
    private val iaLocalImpl: IALocalImpl

) : IARepository {
   override suspend fun sendPostMessage(order: Order): Recipe = iaLocalImpl.sendPostMessage(order)
}