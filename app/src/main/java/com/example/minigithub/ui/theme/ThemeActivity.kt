package com.example.minigithub.ui.theme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.CompoundButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import com.example.minigithub.ViewModelFactory
import com.example.minigithub.databinding.ActivityThemeBinding

class ThemeActivity : AppCompatActivity() {

    private lateinit var themeBinding: ActivityThemeBinding
    private val themeViewModel by viewModels<ThemeViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private var isDarkModeActiveLiveData: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        themeBinding = ActivityThemeBinding.inflate(layoutInflater)
        setContentView(themeBinding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Mengamati perubahan pengaturan tema dan mengaktifkan atau menonaktifkan mode gelap sesuai kebutuhan
        themeViewModel.isDarkModeActiveLiveData.observe(this) { isDarkModeActiveLiveData ->
            this.isDarkModeActiveLiveData = isDarkModeActiveLiveData

            if (isDarkModeActiveLiveData) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                themeBinding.switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                themeBinding.switchTheme.isChecked = false
            }
        }

        // Menangani perubahan status switch dan menyimpan pengaturan tema yang baru
        themeBinding.switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            themeViewModel.saveThemeSetting(isChecked)

            val toastMessage = if (isChecked) "Dark Mode ON" else "Dark Mode OFF"
            showToast(toastMessage)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private var isBackPressedOnce = false

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                if (isBackPressedOnce) {
                    finish()
                    return true
                } else {
                    isBackPressedOnce = true
                    showToast("Press the back button again to exit!" +
                            "")
                    return true
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
