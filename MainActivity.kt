package com.jigong.helper

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val nav = findViewById<BottomNavigationView>(R.id.nav)
        nav.itemIconTintList = null

        nav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_sign -> show(SignFragment())
                R.id.nav_stats -> show(StatsFragment())
                R.id.nav_custom -> show(CustomFragment())
                R.id.nav_system -> show(SystemFragment())
            }
            true
        }
        nav.selectedItemId = R.id.nav_sign
    }

    private fun show(f: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, f).commit()
    }
}
