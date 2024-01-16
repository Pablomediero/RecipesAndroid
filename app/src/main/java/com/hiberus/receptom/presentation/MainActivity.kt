package com.hiberus.receptom.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.fragment.NavHostFragment
import com.hiberus.receptom.R
import com.hiberus.receptom.databinding.ActivityMainBinding
import nl.joery.animatedbottombar.AnimatedBottomBar

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        navegation()
    }
    private fun navegation(){

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fcm_main_container) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bbMenu.setOnTabSelectListener(object : AnimatedBottomBar.OnTabSelectListener {
            override fun onTabSelected(
                lastIndex: Int,
                lastTab: AnimatedBottomBar.Tab?,
                newIndex: Int,
                newTab: AnimatedBottomBar.Tab
            ) {
                when(newIndex){
                    0 -> {navController.navigate(R.id.ingredientsListFragment)}
                    1 -> {navController.navigate(R.id.recipeListFragment)}
                    else -> Log.i("error","error")

                }
            }
        })
    }
}