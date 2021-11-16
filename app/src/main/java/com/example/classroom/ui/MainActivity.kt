package com.example.classroom.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.classroom.R
import com.example.classroom.databinding.ActivityMainBinding
import com.example.classroom.ui.dashboard.DashboardFragment
import com.example.classroom.ui.exams.ExamsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), FragmentCallback {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            bottomNavigationView.setOnNavigationItemSelectedListener(listener)
        }
    }

    private val listener =
        BottomNavigationView.OnNavigationItemSelectedListener { menuItem: MenuItem ->
            var selectedFragment: Fragment? = null
            when (menuItem.itemId) {
                R.id.nav_page1 -> selectedFragment = PageFragment()
                R.id.nav_page2 -> selectedFragment = PageFragment()
                R.id.nav_dashboard -> selectedFragment = DashboardFragment(this)
                R.id.nav_exams -> selectedFragment = ExamsFragment(this)
                R.id.nav_page5 -> selectedFragment = PageFragment()
            }
            supportFragmentManager.beginTransaction().replace(R.id.container, selectedFragment!!)
                .commit()
            true
        }

    override fun openFragmentById(id: Int) {
        binding.bottomNavigationView.selectedItemId = id
    }
}