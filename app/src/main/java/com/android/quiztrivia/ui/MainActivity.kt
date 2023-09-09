package com.android.quiztrivia.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.android.quiztrivia.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showFragment()
    }

    //my work
    private fun showFragment() {
        try {
            /*
            val quizFragment = QuizFragment()
            supportFragmentManager.beginTransaction().add(
                R.id.frame_layout, quizFragment
            ).commit()
             */
            val fragManager = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            fragManager.navController
        } catch (e: Exception) {
            Toast.makeText(this, "Error in adding fragment", Toast.LENGTH_SHORT).show()
        }
    }
}