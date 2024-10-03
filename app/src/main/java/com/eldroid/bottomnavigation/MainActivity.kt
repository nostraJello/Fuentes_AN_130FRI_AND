package com.eldroid.bottomnavigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> loadFragment(CalculatorFragment())
                R.id.nav_search -> loadFragment(ListViewFragment())
                R.id.nav_profile -> loadFragment(ProfileFragment())
            }
            true
        }

        // Set the Profile Fragment as the default fragment on app launch
        bottomNavigationView.selectedItemId = R.id.nav_profile
        loadFragment(ProfileFragment())  // Load the ProfileFragment initially
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}

