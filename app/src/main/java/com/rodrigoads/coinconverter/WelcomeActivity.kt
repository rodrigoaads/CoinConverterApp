package com.rodrigoads.coinconverter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.rodrigoads.coinconverter.databinding.ActivityWelcomeBinding
import com.rodrigoads.coinconverter.viewpager.FragmentsViewPagerManager

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding

    private val welcomePager: ViewPager2 by lazy {
        binding.viewPagerWelcome
    }

    private val tabWelcome: TabLayout by lazy {
        binding.tabLayoutWelcome
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initWelcomePager()
        getClicks()
    }

    private fun getClicks() {
        binding.textViewNext.setOnClickListener {
            welcomePager.currentItem = welcomePager.currentItem + 1
        }

        binding.textViewPrevious.setOnClickListener {
            welcomePager.currentItem = welcomePager.currentItem - 1
        }
    }

    private fun initWelcomePager() {
        val pagerAdapter = FragmentsViewPagerManager(this)
        welcomePager.adapter = pagerAdapter

        TabLayoutMediator(tabWelcome, welcomePager) { tab, position ->
        }.attach()

        welcomePager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        binding.textViewPrevious.visibility = View.GONE
                        binding.textViewNext.visibility = View.VISIBLE
                    }
                    1 -> {
                        binding.textViewPrevious.visibility = View.VISIBLE
                        binding.textViewNext.visibility = View.VISIBLE
                    }
                    2 -> {
                        binding.textViewPrevious.visibility = View.VISIBLE
                        binding.textViewNext.visibility = View.GONE
                    }
                }
                super.onPageSelected(position)
            }
        })
    }
}