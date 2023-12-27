package com.example.minigithub.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minigithub.data.FavoriteRepository
import com.example.minigithub.data.FavoriteEntity
import com.example.minigithub.response.UserDetailResponse
import com.example.minigithub.remote.ApiConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val favoriteRepository: FavoriteRepository) : ViewModel() {

    private val _detailUser = MutableLiveData<UserDetailResponse>()
    private val _isLoading = MutableLiveData<Boolean>()

    val detailUser: LiveData<UserDetailResponse> get() = _detailUser
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun insert(user: FavoriteEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteRepository.insert(user)
        }
    }

    fun delete(user: FavoriteEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteRepository.delete(user)
        }
    }

    // Fungsi untuk mencari pengguna favorit berdasarkan username
    fun findUserByUsername(username: String) = favoriteRepository.getFavoriteUserByUsername(username)

    // Fungsi untuk mencari data detail pengguna dari API
    fun findData(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailingUser(username)
        client.enqueue(object : Callback<UserDetailResponse> {
            override fun onResponse(call: Call<UserDetailResponse>, response: Response<UserDetailResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    responseBody?.let {
                        // Memperbarui LiveData detailUser dengan data hasil panggilan API
                        _detailUser.value = it
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) { _isLoading.value = false
            }
        })
    }

    companion object {
        private const val TAG = "DetailViewModel"
    }
}
