package com.example.minigithub.ui.main

import android.util.Log
import androidx.lifecycle.*
import com.example.minigithub.ItemsItem
import com.example.minigithub.UserResponse
import com.example.minigithub.remote.ApiConfig
import com.example.minigithub.ui.theme.SettingPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val preferences: SettingPreference) : ViewModel() {
    // LiveData untuk menyimpan daftar pengguna
    private val _listUser = MutableLiveData<List<ItemsItem>>()
    val listUser: LiveData<List<ItemsItem>> = _listUser

    // LiveData untuk menandakan apakah data sedang dimuat
    private val _prosesIsLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _prosesIsLoading

    // LiveData untuk memantau perubahan pada input pencarian pengguna
    val searchUser = MutableLiveData<String>()

    // Mendapatkan tema yang dipilih dari preferensi
    fun getTheme() = preferences.getThemeSetting().asLiveData()

    companion object {
        private const val TAG = "CustomizedMainActivity"
        private const val QUERY = "a" // Kata kunci pencarian default
    }

    init {
        // Menginisialisasi ViewModel dengan melakukan pencarian awal dengan kata kunci default
        getUser(QUERY)
    }

    // Fungsi untuk mengambil daftar pengguna berdasarkan kata kunci pencarian
    fun getUser(keyword: String) {
        _prosesIsLoading.value = true // Menandakan bahwa data sedang dimuat
        val api = ApiConfig.getApiService().getSearchUser(keyword)
        api.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                _prosesIsLoading.value = false // Menandakan bahwa proses pembebanan data telah selesai
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listUser.value = responseBody.items // Memperbarui LiveData dengan data pengguna baru
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _prosesIsLoading.value = false // Menandakan bahwa proses pembebanan data telah selesai
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    // Factory untuk membuat instance MainViewModel
    class Factory(private val preferences: SettingPreference) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            MainViewModel(preferences) as T
    }
}
