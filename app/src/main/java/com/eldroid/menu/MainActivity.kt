package com.eldroid.menu

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_first_option -> {
                // Navigate to another fragment
                loadFirstFragment()
                return true
            }
            R.id.action_second_option -> {
                // Show the dialog and hide the FrameLayout
                val dialogFragment = SecondDialogFragment()
                dialogFragment.show(supportFragmentManager, "SecondDialogFragment")
                return true
            }
            R.id.action_exit -> {
                // Exit app
                exitApp()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun resizeDrawable(drawable: Drawable, width: Int, height: Int): Drawable {
        val bitmap = (drawable as BitmapDrawable).bitmap
        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false)
        return BitmapDrawable(resources, resizedBitmap)
    }

    fun loadFirstFragment() {
        val fragment = FirstFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainLayout, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun exitApp() {
        finishAffinity() // This will close the app
    }
}
