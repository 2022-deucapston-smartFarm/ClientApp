package com.android.smartfarm.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.android.smartfarm.R
import com.android.smartfarm.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        val navmainFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment//바텀네비게이터 설정
        val navController = navmainFragment.navController
        binding.mainBottomNav.setupWithNavController(navController)

    }


}