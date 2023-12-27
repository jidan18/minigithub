package com.example.minigithub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.minigithub.ui.main.CustomizedMainActivity
import com.google.android.material.snackbar.Snackbar
import kotlin.random.Random

class Splash : AppCompatActivity() {
    private lateinit var delayHandler: Handler
    private var splashDelayMillis = 3000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // Inisialisasi handler untuk menangani penundaan
        delayHandler = Handler(Looper.getMainLooper())

        // Menentukan waktu penundaan splash screen dengan cara yang unik
        splashDelayMillis = Random.nextLong(1000, 5000)

        // PostDelayed akan menjalankan intent ke MainActivity setelah splashDelayMillis
        delayHandler.postDelayed({
            // Menampilkan snackbar untuk memberi tahu pengguna tentang waktu yang tersisa
            Snackbar.make(
                findViewById(android.R.id.content),
                "Aplikasi akan diluncurkan dalam ${splashDelayMillis / 1000} detik",
                Snackbar.LENGTH_LONG
            ).show()

            // Memulai CustomizedMainActivity setelah splashDelayMillis
            val mainActivityIntent = Intent(this, CustomizedMainActivity::class.java)
            startActivity(mainActivityIntent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }, splashDelayMillis)
    }
}
