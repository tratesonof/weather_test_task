package com.example.weather_task

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weather_task.feature.main_screen.ui.MainScreenFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, MainScreenFragment())
            .commit()
    }
}
