package com.hiberus.receptom.di

import androidx.room.Room
import com.google.gson.Gson
import com.hiberus.receptom.data.database.RecipeDataImpl
import com.hiberus.receptom.data.database.RecipeLocalImpl
import com.hiberus.receptom.data.database.service.AppDatabase
import com.hiberus.receptom.data.database.mapper.CacheMapper
import com.hiberus.receptom.data.remote.service.ApiClient
import com.hiberus.receptom.data.remote.service.ChatGPTService
import com.hiberus.receptom.data.remote.IADataImpl
import com.hiberus.receptom.data.remote.IALocalImpl
import com.hiberus.receptom.data.remote.mapper.ResponseMapper
import com.hiberus.receptom.domain.repository.IARepository
import com.hiberus.receptom.domain.repository.RecipeRepository
import com.hiberus.receptom.domain.usecase.ia.GetIAResponseUseCase
import com.hiberus.receptom.domain.usecase.recipe.AddRecipeUseCase
import com.hiberus.receptom.domain.usecase.recipe.DeleteRecipeUseCase
import com.hiberus.receptom.domain.usecase.recipe.GetAllRecipesUseCase
import com.hiberus.receptom.domain.usecase.recipe.GetRecipeUseCase
import com.hiberus.receptom.presentation.viewmodel.ReceptomViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val baseModule = module {
    single<AppDatabase> { Room.databaseBuilder(androidContext(), AppDatabase::class.java, "receptomDB").build() }
}

val iaModule = module {
    single<ChatGPTService> { ApiClient.provideApiService()}
}

val appModule = module {
    factory { Gson() }
    factory { RecipeLocalImpl(get(), get()) }
    factory { IALocalImpl(get(), get(), get()) }
    factory<RecipeRepository> { RecipeDataImpl(get()) }
    factory<IARepository> { IADataImpl(get()) }
    factory { ResponseMapper() }
    factory { CacheMapper() }
    factory { GetAllRecipesUseCase(get()) }
    factory { GetRecipeUseCase(get()) }
    factory { AddRecipeUseCase(get()) }
    factory { DeleteRecipeUseCase(get()) }
    factory { GetIAResponseUseCase(get()) }

    viewModel { ReceptomViewModel(get(), get(), get(), get(), get()) }
}