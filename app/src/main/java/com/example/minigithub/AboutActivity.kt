package com.example.minigithub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Mengatur tata letak aktivitas dengan layout activity_about
        setContentView(R.layout.activity_about)
        // Mengatur judul ActionBar dengan string "about"
        val aboutTitle = getString(R.string.about)
        supportActionBar?.title = aboutTitle
        // Menampilkan tombol panah navigasi ke atas pada ActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        // Menampilkan pesan toast saat kembali
        val exitMessage = "You left the About page"
        Toast.makeText(this, exitMessage, Toast.LENGTH_SHORT).show()

        // Mengakhiri aktivitas saat tombol panah navigasi ke atas ditekan
        finish()

        // Memanggil metode onSupportNavigateUp dari kelas induk
        return super.onSupportNavigateUp()
    }
}
