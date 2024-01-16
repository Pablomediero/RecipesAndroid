package com.hiberus.receptom.presentation.recipe_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hiberus.receptom.databinding.FragmentRecipeListBinding
import com.hiberus.receptom.presentation.recipe_list.adapter.RecipeListAdapter
import com.hiberus.receptom.presentation.common.ResourceState
import com.hiberus.receptom.presentation.viewmodel.ReceptomViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class RecipeListFragment : Fragment() {
    private val binding: FragmentRecipeListBinding by lazy {
        FragmentRecipeListBinding.inflate(layoutInflater)
    }
    private val recipeListAdapter = RecipeListAdapter()
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
        initRecyclerViewData()
        initUI()
        initListeners()
    }

    private fun initRecyclerViewData() {
        receptomViewModel.getRecipesList()
        receptomViewModel.recipeListLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ResourceState.Loading -> {
                    binding.pbRecipeList.visibility = View.VISIBLE
                    binding.tvRecipeEmptyRv.visibility = View.VISIBLE
                    binding.tvRecipeInfo.visibility = View.GONE
                }

                is ResourceState.Success -> {
                    binding.pbRecipeList.visibility = View.GONE
                    binding.tvRecipeEmptyRv.visibility = View.GONE
                    binding.tvRecipeInfo.visibility = View.VISIBLE
                    binding.rvRecipeList.visibility = View.VISIBLE
                    recipeListAdapter.submitList(state.result)
                }

                is ResourceState.Error -> {
                    binding.pbRecipeList.visibility = View.GONE
                    binding.tvRecipeEmptyRv.visibility = View.GONE
                    Toast.makeText(requireContext(), state.error, Toast.LENGTH_LONG).show()
                }

                is ResourceState.Empty -> {
                    binding.pbRecipeList.visibility = View.GONE
                    binding.tvRecipeEmptyRv.visibility = View.VISIBLE
                    binding.tvRecipeInfo.visibility = View.VISIBLE
                    binding.rvRecipeList.visibility = View.GONE
                }

                else -> {}
            }
        }
    }

    private fun initUI() {
        binding.rvRecipeList.adapter = recipeListAdapter
        binding.rvRecipeList.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initListeners() {
        recipeListAdapter.onClickListener = { recipe ->
            findNavController().navigate(
                RecipeListFragmentDirections.actionRecipeListFragmentToRecipeDetailFragment(
                    recipe.id
                )
            )
        }
    }
}
