package com.hiberus.receptom.presentation.recipe_preview

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.hiberus.receptom.R
import com.hiberus.receptom.databinding.DialogActionConfirmAddBinding
import com.hiberus.receptom.databinding.DialogActionConfirmNextBinding
import com.hiberus.receptom.databinding.FragmentRecipePreviewBinding
import com.hiberus.receptom.model.local.Ingredient
import com.hiberus.receptom.model.local.Order
import com.hiberus.receptom.model.local.Recipe
import com.hiberus.receptom.presentation.common.ResourceState
import com.hiberus.receptom.presentation.viewmodel.ReceptomViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel


class RecipePreviewFragment : Fragment() {
    private val binding: FragmentRecipePreviewBinding by lazy {
        FragmentRecipePreviewBinding.inflate(layoutInflater)
    }
    private val receptomViewModel: ReceptomViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
    }

    private fun getData() {
        receptomViewModel.iaMessageLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is ResourceState.Loading -> {
                    setVisibility(false)
                }

                is ResourceState.Success -> {
                    setVisibility(true)
                    initUi(it.result)
                    initListeners(it.result)
                }

                is ResourceState.Error -> {
                    setVisibility(true)
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_LONG).show()
                }

                else -> {}
            }
        }
    }

    private fun setVisibility(isTrue: Boolean) {
        binding.pbRecipePreview.visibility = if (isTrue) View.GONE else View.VISIBLE
        binding.tvRecipePreviewName.visibility = if (isTrue) View.VISIBLE else View.GONE
        binding.tvRecipePreviewIngredientsInfo.visibility = if (isTrue) View.VISIBLE else View.GONE
        binding.tvRecipePreviewIngredients.visibility = if (isTrue) View.VISIBLE else View.GONE
        binding.tvRecipePreviewInstructionsInfo.visibility = if (isTrue) View.VISIBLE else View.GONE
        binding.tvRecipePreviewInstructions.visibility = if (isTrue) View.VISIBLE else View.GONE
        binding.tvRecipePreviewServingInfo.visibility = if (isTrue) View.VISIBLE else View.GONE
        binding.tvRecipePreviewServing.visibility = if (isTrue) View.VISIBLE else View.GONE
    }

    private fun initUi(recipe: Recipe) {
        var ingredientsList = ""
        recipe.ingredients.forEach { ingredientName ->
            ingredientsList += " ${ingredientName.name} \n"
        }
        binding.tvRecipePreviewName.text = recipe.name
        binding.tvRecipePreviewInstructions.text = recipe.instructions
        binding.tvRecipePreviewIngredients.text = ingredientsList
        binding.tvRecipePreviewServing.text = recipe.serving.toString()
    }

    private fun initListeners(recipe: Recipe) {
        binding.btRecipePreviewSave.setOnClickListener {
            receptomViewModel.addRecipe(recipe)
            receptomViewModel.addRecipeListLiveData.observe(viewLifecycleOwner){
                when (it) {
                    is ResourceState.Loading -> {}

                    is ResourceState.Success -> {
                        addRecipeConfirmDialog()
                        findNavController().navigate(R.id.ingredientsListFragment)
                    }

                    is ResourceState.Error -> {
                        Toast.makeText(requireContext(), it.error, Toast.LENGTH_LONG).show()
                    }
                    else -> {}
                }
            }
        }
        binding.btRecipePreviewGenerateOtherRecipe.setOnClickListener {
            val recipeNameParse = mutableListOf(
                Ingredient(name = recipe.name)
            )
            nextRecipeDialog(recipeNameParse)
        }
    }

    private fun nextRecipeDialog(listIngredients: MutableList<Ingredient>) {
        val dialog = Dialog(requireContext())
        val dialogBinding = DialogActionConfirmNextBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)

        dialogBinding.btnDialogConfirmNextNo.setOnClickListener {
            dialog.dismiss()
        }
        dialogBinding.btnDialogConfirmNextYes.setOnClickListener {
            receptomViewModel.sendPostMessage(Order(listIngredients, 2))
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun addRecipeConfirmDialog() {
        val dialog = Dialog(requireContext())
        val dialogBinding = DialogActionConfirmAddBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)

        dialogBinding.btnDialogConfirmAdd.setOnClickListener{
            dialog.dismiss()
        }

        dialog.show()
    }

}