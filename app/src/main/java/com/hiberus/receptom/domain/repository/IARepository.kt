package com.hiberus.receptom.domain.repository

import com.hiberus.receptom.model.local.Order
import com.hiberus.receptom.model.local.Recipe

interface IARepository {
    suspend fun sendPostMessage(order: Order): Recipe
}