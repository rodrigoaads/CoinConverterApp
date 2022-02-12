package com.rodrigoads.coinconverter.fragments.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.rodrigoads.coinconverter.databinding.FragmentThirdWelcomeBinding
import com.rodrigoads.coinconverter.singleton.Datastore.dataStore
import kotlinx.coroutines.launch

class ThirdWelcomeFragment() : Fragment() {
    private lateinit var binding: FragmentThirdWelcomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThirdWelcomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getButtonClick()
    }

    private fun getButtonClick() {
        binding.buttonInit.setOnClickListener {
            lifecycleScope.launch {
                val NEW_USER = booleanPreferencesKey("new_user")
                context?.dataStore?.edit {
                    it[NEW_USER] = false
                }
                activity?.finish()
            }
        }
    }
}