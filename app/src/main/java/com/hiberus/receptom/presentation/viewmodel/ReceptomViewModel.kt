package com.hiberus.receptom.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hiberus.receptom.domain.usecase.ia.GetIAResponseUseCase
import com.hiberus.receptom.domain.usecase.recipe.AddRecipeUseCase
import com.hiberus.receptom.domain.usecase.recipe.DeleteRecipeUseCase
import com.hiberus.receptom.domain.usecase.recipe.GetAllRecipesUseCase
import com.hiberus.receptom.domain.usecase.recipe.GetRecipeUseCase
import com.hiberus.receptom.model.local.Order
import com.hiberus.receptom.model.local.Recipe
import com.hiberus.receptom.presentation.common.ResourceState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

typealias GetRecipeResourceState = ResourceState<Recipe>
typealias GetAllRecipesResourceState = ResourceState<List<Recipe>>
typealias AddRecipeResourceState = ResourceState<Void?>
typealias DeleteRecipeResourceState = ResourceState<Void?>
typealias SendPostResourceState = ResourceState<Recipe>

class ReceptomViewModel(
    private val getAllRecipesUseCase: GetAllRecipesUseCase,
    private val addRecipesUseCase: AddRecipeUseCase,
    private val getRecipeUseCase: GetRecipeUseCase,
    private val deleteRecipeUseCase: DeleteRecipeUseCase,
    private val getIAResponseUseCase: GetIAResponseUseCase,
) : ViewModel() {

    // RECIPES
    private val _recipeListLiveData = MutableLiveData<GetAllRecipesResourceState>()
    val recipeListLiveData: LiveData<GetAllRecipesResourceState> get() = _recipeListLiveData

    private val _deleteRecipeListLiveData = MutableLiveData<DeleteRecipeResourceState>()
    val deleteRecipeListLiveData: LiveData<DeleteRecipeResourceState> get() = _deleteRecipeListLiveData

    private val _recipeLiveData = MutableLiveData<GetRecipeResourceState>()
    val recipeLiveData: LiveData<GetRecipeResourceState> get() = _recipeLiveData

    private val _addRecipeListLiveData = MutableLiveData<AddRecipeResourceState>()
    val addRecipeListLiveData: LiveData<AddRecipeResourceState> get() = _addRecipeListLiveData

    // IA
    private val _iaMessageLiveData = MutableLiveData<SendPostResourceState>()
    val iaMessageLiveData: LiveData<SendPostResourceState> get() = _iaMessageLiveData

    fun sendPostMessage(order: Order) {
        _iaMessageLiveData.value = ResourceState.Loading()
        viewModelScope.launch {
            try {
                val response = getIAResponseUseCase.execute(order)
                withContext(Dispatchers.Main) {
                    _iaMessageLiveData.value = ResourceState.Success(response)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("okhttp",e.message.toString())
                    _iaMessageLiveData.value = ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }

    fun getRecipesList() {
        _recipeListLiveData.value = ResourceState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val recipes = getAllRecipesUseCase.execute()
                withContext(Dispatchers.Main) {
                    if (recipes.isEmpty())
                        _recipeListLiveData.value = ResourceState.Empty()
                    else
                        _recipeListLiveData.value = ResourceState.Success(recipes)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _recipeListLiveData.value = ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }

    fun getRecipe(recipeId: Int) {
        _recipeLiveData.value = ResourceState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val recipe = getRecipeUseCase.execute(recipeId)
                withContext(Dispatchers.Main) {
                    _recipeLiveData.value = ResourceState.Success(recipe)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _recipeLiveData.value = ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }

    fun addRecipe(recipe: Recipe) {
        _addRecipeListLiveData.value = ResourceState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                addRecipesUseCase.execute(recipe)
                withContext(Dispatchers.Main) {
                    _addRecipeListLiveData.value = ResourceState.Success(null)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _addRecipeListLiveData.value = ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }

    fun deleteRecipe(recipe: Recipe) {
        _deleteRecipeListLiveData.value = ResourceState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                deleteRecipeUseCase.execute(recipe)
                withContext(Dispatchers.Main) {
                    _deleteRecipeListLiveData.value = ResourceState.Success(null)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _deleteRecipeListLiveData.value =
                        ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }

}