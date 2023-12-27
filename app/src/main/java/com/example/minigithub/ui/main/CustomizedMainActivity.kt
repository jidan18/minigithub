package com.example.minigithub.ui.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.minigithub.AboutActivity
import com.example.minigithub.ItemsItem
import com.example.minigithub.R
import com.example.minigithub.ViewModelFactory
import com.example.minigithub.adapter.UserAdapter
import com.example.minigithub.databinding.ActivityMainBinding
import com.example.minigithub.ui.favorite.FavoriteActivity
import com.example.minigithub.ui.theme.ThemeActivity

class CustomizedMainActivity : AppCompatActivity() {

    private lateinit var customizedBinding: ActivityMainBinding
    private val customizedViewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private val customizedList = ArrayList<ItemsItem>()
    private val customizedUserAdapter = UserAdapter(customizedList)

    private lateinit var customizedLayoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        customizedBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(customizedBinding.root)

        customizedLayoutManager = LinearLayoutManager(this)
        customizedBinding.rvUsers.layoutManager = customizedLayoutManager

        customizedBinding.rvUsers.adapter = customizedUserAdapter

        customizedViewModel.listUser.observe(this) { item ->
            if (item.isNotEmpty()) {
                // Jika ada hasil, tampilkan hasil di RecyclerView
                setUserData(item)
            } else {
                // Jika hasil kosong, tampilkan toast "Username tidak ditemukan"
                showToast("Username not found")
            }
        }

        customizedViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)

        customizedViewModel.searchUser.observe(this) { query ->
            searchView.setQuery(query.toString(), false)
        }

        customizedViewModel.getTheme().observe(this) { isDarkModeActive: Boolean ->
            val nightMode = if (isDarkModeActive) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
            AppCompatDelegate.setDefaultNightMode(nightMode)
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                customizedViewModel.getUser(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
                customizedViewModel.searchUser.postValue(query)
                return false
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_favorite -> {
                Intent(this, FavoriteActivity::class.java).also {
                    startActivity(it)
                }
            }
            R.id.menu_theme -> {
                val intent = Intent(this, ThemeActivity::class.java)
                startActivity(intent)
            }
            R.id.action_list -> {
                customizedLayoutManager = LinearLayoutManager(this)
                customizedBinding.rvUsers.layoutManager = customizedLayoutManager
            }
            R.id.action_grid -> {
                customizedLayoutManager = GridLayoutManager(this, 2)
                customizedBinding.rvUsers.layoutManager = customizedLayoutManager
            }
            R.id.about -> {
                startActivity(Intent(this, AboutActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUserData(listUser: List<ItemsItem>) {
        customizedBinding.rvUsers.adapter = UserAdapter(listUser)
    }

    private fun showLoading(isLoading: Boolean) {
        customizedBinding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
