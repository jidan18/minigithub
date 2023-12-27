package com.example.minigithub.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.minigithub.R
import com.example.minigithub.ViewModelFactory
import com.example.minigithub.adapter.SectionsPagerAdapter
import com.example.minigithub.data.FavoriteEntity
import com.example.minigithub.databinding.ActivityDetailBinding
import com.example.minigithub.response.UserDetailResponse
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var bindingUserGithub: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>() {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var favUser: FavoriteEntity
    private var isFavorite: Boolean = false

    companion object {
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )

        const val TAG = "DetailActivity"
        const val KEY = "Data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingUserGithub = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(bindingUserGithub.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = bindingUserGithub.viewPager

        sectionsPagerAdapter.selectedUsername = intent.getStringExtra(KEY).toString()

        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = bindingUserGithub.tabs

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

        val username = intent.getStringExtra(KEY)

        if (username != null) {
            detailViewModel.findData(username)
        }

        detailViewModel.detailUser.observe(this) { detail ->
            detailData(detail)
            favUser = FavoriteEntity(username.toString(), detail.avatarUrl)
        }

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        detailViewModel.findUserByUsername(username.toString()).observe(this) { user: FavoriteEntity? ->
            if (user != null) {
                isFavorite = true
                bindingUserGithub.favoriteBtn.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.favorite
                    )
                )
            } else {
                isFavorite = false
                bindingUserGithub.favoriteBtn.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.favorite_border
                    )
                )
            }
        }
        bindingUserGithub.favoriteBtn.setOnClickListener { toggleFavorite() }
    }

    private fun detailData(detail: UserDetailResponse) {
        bindingUserGithub.apply {
            tvUsername.text = detail.login
            tvName.text = detail.name
            tvFollower.text = resources.getString(R.string.follower_count, detail.followers)
            tvFollowing.text = resources.getString(R.string.following_count, detail.following)
            Glide.with(this@DetailActivity)
                .load(detail.avatarUrl)
                .into(photoDetail)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLoading(isLoading: Boolean) {
        bindingUserGithub.progressBar2.isVisible = isLoading
    }

    private fun toggleFavorite() {
        if (isFavorite) {
            detailViewModel.delete(favUser)
            showToast("Removed from favorites")
        } else {
            detailViewModel.insert(favUser)
            showToast("Added to favorites")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
