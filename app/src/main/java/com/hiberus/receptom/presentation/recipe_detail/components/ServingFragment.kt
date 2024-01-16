package com.hiberus.receptom.presentation.recipe_detail.components

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hiberus.receptom.databinding.FragmentServingBinding
import com.hiberus.receptom.model.local.Recipe


class ServingFragment ( private val recipe: Recipe): Fragment() {

    private val binding: FragmentServingBinding by lazy {
        FragmentServingBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        binding.tvComponentServing.text = recipe.serving.toString()
    }
}