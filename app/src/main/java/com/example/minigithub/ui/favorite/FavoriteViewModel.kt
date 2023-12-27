package com.example.minigithub.ui.favorite

import androidx.lifecycle.ViewModel
import com.example.minigithub.data.FavoriteRepository

class FavoriteViewModel (private val noteRepository: FavoriteRepository) : ViewModel() {

    fun getUserFavorite() = noteRepository.getAllUser()

}