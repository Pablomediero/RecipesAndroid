package com.hiberus.receptom.presentation.recipe_detail

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.hiberus.receptom.databinding.DialogActionConfirmDeleteBinding
import com.hiberus.receptom.databinding.FragmentRecipeDetailBinding
import com.hiberus.receptom.model.local.Recipe
import com.hiberus.receptom.presentation.common.ResourceState
import com.hiberus.receptom.presentation.recipe_detail.components.IngredientsFragment
import com.hiberus.receptom.presentation.recipe_detail.components.InstructionsFragment
import com.hiberus.receptom.presentation.recipe_detail.components.ServingFragment
import com.hiberus.receptom.presentation.viewmodel.ReceptomViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import java.lang.IllegalArgumentException

class RecipeDetailFragment : Fragment() {
    private val binding: FragmentRecipeDetailBinding by lazy {
        FragmentRecipeDetailBinding.inflate(layoutInflater)
    }
    private val receptomViewModel: ReceptomViewModel by activityViewModel()
    private val args: RecipeDetailFragmentArgs by navArgs()
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
        receptomViewModel.getRecipe(args.recipeId.toInt())
        receptomViewModel.recipeLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ResourceState.Loading -> {
                    setVisibility(false)
                }

                is ResourceState.Success -> {
                    setVisibility(true)
                    initUI(state.result)
                    initListeners(state.result)
                }

                is ResourceState.Error -> {
                    setVisibility(true)
                    Toast.makeText(requireContext(), state.error, Toast.LENGTH_LONG).show()
                }

                else -> {}
            }
        }
    }

    private fun setVisibility(isTrue: Boolean) {
        binding.pbDetailRecipe.visibility = if (isTrue) View.GONE else View.VISIBLE
        binding.tvDetailRecipeName.visibility = if (isTrue) View.VISIBLE else View.GONE
        binding.fbRecipeDetailBack.visibility = if (isTrue) View.VISIBLE else View.GONE
        binding.vpRecipeDetailViewpagerComponents.visibility =
            if (isTrue) View.VISIBLE else View.GONE
    }

    private fun initUI(recipe: Recipe) {
        binding.tvDetailRecipeName.text = recipe.name
        binding.vpRecipeDetailViewpagerComponents.adapter = RecipeDetailViewPagerAdapter(this, recipe)
        TabLayoutMediator(
            binding.tlRecipeDetailTabsComponents,
            binding.vpRecipeDetailViewpagerComponents
        ) { tab, position ->
            tab.text = when (position) {
                0 -> "Ingredientes"
                1 -> "Preparación"
                else -> "Cantidad"
            }
        }.attach()
    }

    private fun initListeners(recipe: Recipe) {
        binding.fbRecipeDetailBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btRecipeDetailDelete.setOnClickListener {
            deleteConfirmDialog(recipe)

        }
    }

    private fun deleteConfirmDialog(recipe: Recipe) {
        val dialog = Dialog(requireContext())
        val dialogBinding = DialogActionConfirmDeleteBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)

        dialogBinding.btnDialogConfirmDeleteNo.setOnClickListener {
            dialog.dismiss()
        }
        dialogBinding.btnDialogConfirmDeleteYes.setOnClickListener {
            receptomViewModel.deleteRecipe(recipe)
            receptomViewModel.deleteRecipeListLiveData.observe(viewLifecycleOwner) { state ->
                when (state) {
                    is ResourceState.Loading -> {
                        binding.pbDetailRecipe.visibility = View.VISIBLE
                    }

                    is ResourceState.Success -> {
                        binding.pbDetailRecipe.visibility = View.GONE
                        findNavController().popBackStack()
                    }

                    is ResourceState.Error -> {
                        binding.pbDetailRecipe.visibility = View.GONE
                        Toast.makeText(requireContext(), state.error, Toast.LENGTH_LONG).show()
                    }

                    else -> {}
                }
            }
            dialog.dismiss()
        }
        dialog.show()
    }

    private inner class RecipeDetailViewPagerAdapter(fragment: Fragment, private val recipe: Recipe) :
        FragmentStateAdapter(fragment) {
        override fun getItemCount() = 3
        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> {
                    IngredientsFragment(recipe)
                }

                1 -> {
                    InstructionsFragment(recipe)
                }

                2 -> {
                    ServingFragment(recipe)
                }

                else -> throw IllegalArgumentException("Invalid position")
            }
        }
    }
}