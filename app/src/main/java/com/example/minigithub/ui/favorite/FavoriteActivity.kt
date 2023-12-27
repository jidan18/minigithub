package com.example.minigithub.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.minigithub.ItemsItem
import com.example.minigithub.ViewModelFactory
import com.example.minigithub.adapter.UserAdapter
import com.example.minigithub.data.FavoriteEntity
import com.example.minigithub.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {

    private lateinit var favoriteBinding: ActivityFavoriteBinding
    private val favoriteViewModel by viewModels<FavoriteViewModel>() {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(favoriteBinding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.title = "Favorite User"

        // Konfigurasi RecyclerView
        val layoutManager = LinearLayoutManager(this)
        favoriteBinding.rvFavUser.layoutManager = layoutManager


        // Mengamati perubahan data daftar pengguna favorit
        favoriteViewModel.getUserFavorite().observe(this) { users ->
            val items = users.map {
                ItemsItem(login = it.username, avatarUrl = it.avatarUrl)
            }

            // Menggunakan UserAdapter untuk menampilkan daftar pengguna favorit
            favoriteBinding.rvFavUser.adapter = UserAdapter(items)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
