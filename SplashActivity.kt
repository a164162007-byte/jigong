package com.jigong.helper

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val tvSkip = findViewById<TextView>(R.id.tv_skip)
        tvSkip.text = "跳过"

        tvSkip.setOnClickListener { goMain() }

        AdManager.loadAndShowSplashAd(
            activity = this,
            onAdDismiss = { goMain() },
            onAdError = { goMain() }
        )
    }

    private fun goMain() {
        BackupUtils.autoBackupOnStartOrClose(this)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        BackupUtils.autoBackupOnStartOrClose(this)
    }
}
