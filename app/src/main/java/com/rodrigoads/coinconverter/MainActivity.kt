package com.rodrigoads.coinconverter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.rodrigoads.coinconverter.databinding.ActivityMainBinding
import com.rodrigoads.coinconverter.singleton.Datastore.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(binding.fragmentContainerView.id) as NavHostFragment
        navController = navHostFragment.navController

        initToolbar()
        getNewUser()
    }

    private fun getNewUser() {
        val NEW_USER = booleanPreferencesKey("new_user")
        val newUserFlow: Flow<Boolean> = application.dataStore.data
            .map {
                it[NEW_USER] ?: true
            }

        lifecycleScope.launch {
            newUserFlow.collect {
                if (it) {
                    val intent = Intent(this@MainActivity, WelcomeActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    private fun initToolbar() {
        setSupportActionBar(binding.mainToolbar)
        val appBarConfiguration = AppBarConfiguration(navController.graph, binding.mainDrawerLayout)
        binding.mainToolbar.setupWithNavController(navController, appBarConfiguration)
        binding.mainNavigationView.setupWithNavController(navController)
    }
}