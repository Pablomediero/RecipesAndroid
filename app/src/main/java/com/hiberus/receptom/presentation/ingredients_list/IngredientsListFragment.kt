package com.hiberus.receptom.presentation.ingredients_list

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hiberus.receptom.R
import com.hiberus.receptom.databinding.DialogAddIngredientBinding
import com.hiberus.receptom.databinding.FragmentIngredientsListBinding
import com.hiberus.receptom.model.local.Ingredient
import com.hiberus.receptom.model.local.Order
import com.hiberus.receptom.presentation.ingredients_list.adapter.IngredientsListAdapter
import com.hiberus.receptom.presentation.viewmodel.ReceptomViewModel

import org.koin.androidx.viewmodel.ext.android.activityViewModel


class IngredientsListFragment() : Fragment() {
    private val binding: FragmentIngredientsListBinding by lazy {
        FragmentIngredientsListBinding.inflate(layoutInflater)
    }
    private val ingredientsListAdapter = IngredientsListAdapter()
    private val ingredientsList = mutableListOf<Ingredient>()
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
        ingredientsListAdapter.submitList(ingredientsList)
    }

    private fun initUI() {
        binding.tvIngredientEmptyRv.visibility = View.VISIBLE
        binding.rvIngredientsList.adapter = ingredientsListAdapter
        binding.rvIngredientsList.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initListeners() {
        binding.btIngredientsSearch.setOnClickListener {
            if(ingredientsList.isNotEmpty()){
                receptomViewModel.sendPostMessage(Order(ingredientsList.toList(), 1))
                findNavController().navigate(R.id.action_ingredientsListFragment_to_recipePreviewFragment)
            }else{
                Toast.makeText(requireContext(), "Lista vacia, introduce algun ingrediente", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btIngredientsAdd.setOnClickListener {
            showAddIngredientDialog()
        }

        ingredientsListAdapter.onIconClicked = {
            ingredientsList.removeAt(it)
            updateIngredientsList()
        }
    }

    private fun showAddIngredientDialog() {
        val dialog = Dialog(requireContext())
        val dialogBinding = DialogAddIngredientBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)

        dialogBinding.etDialogAddIngredientName.addTextChangedListener {
            dialogBinding.btnDialogAddIngredient.isEnabled = it?.isNotBlank() ?: false
        }
        dialogBinding.btnDialogAddIngredient.setOnClickListener {
            val name = dialogBinding.etDialogAddIngredientName.text.toString()
            ingredientsList.add(Ingredient(name = name))
            updateIngredientsList()
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun updateIngredientsList() {
        binding.rvIngredientsList.adapter = ingredientsListAdapter
    }

}