package com.eldroid.news

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private var isDualPane = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Check if the detail container is present, indicating landscape mode
        isDualPane = findViewById<View?>(R.id.detail_fragment_container) != null

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.list_fragment_container, NewsListFragment())
                .commit()
        }
    }

    fun showNewsDetails(newsTitle: String, newsContent: String) {
        val detailFragment = NewsDetailFragment.newInstance(newsTitle, newsContent)

        if (isDualPane) {
            // Replace the detail fragment in the right-side container in landscape mode
            supportFragmentManager.beginTransaction()
                .replace(R.id.detail_fragment_container, detailFragment)
                .commit()
        } else {
            // In portrait mode, replace the entire container with the detail fragment
            supportFragmentManager.beginTransaction()
                .replace(R.id.list_fragment_container, detailFragment)
                .addToBackStack(null)
                .commit()
        }
    }
}

