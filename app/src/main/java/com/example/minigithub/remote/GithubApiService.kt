package com.example.minigithub.remote

import com.example.minigithub.ItemsItem
import com.example.minigithub.UserResponse
import com.example.minigithub.response.UserDetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Interface yang mendefinisikan endpoint-endpoint yang akan diakses dari GitHub API.
 */
interface GithubApiService {

    /**
     * Mendapatkan daftar pengguna GitHub berdasarkan pencarian.
     *
     * @param query Kata kunci pencarian.
     * @return [UserResponse] yang berisi daftar pengguna yang cocok dengan hasil pencarian.
     */
    @GET("search/users")
    fun getSearchUser(
        @Query("q") query: String
    ): Call<UserResponse>

    /**
     * Mendapatkan detail pengguna GitHub berdasarkan username.
     *
     * @param username Username pengguna GitHub.
     * @return [UserDetailResponse] yang berisi detail pengguna.
     */
    @GET("users/{username}")
    fun getDetailingUser(@Path("username") username: String): Call<UserDetailResponse>

    /**
     * Mendapatkan daftar pengikut (followers) dari pengguna GitHub berdasarkan username.
     *
     * @param username Username pengguna GitHub.
     * @return [List<ItemsItem>] yang berisi daftar pengikut pengguna.
     */
    @GET("users/{username}/followers")
    fun getUserFollowers(@Path("username") username: String): Call<List<ItemsItem>>

    /**
     * Mendapatkan daftar pengguna yang diikuti (following) oleh pengguna GitHub berdasarkan username.
     *
     * @param username Username pengguna GitHub.
     * @return [List<ItemsItem>] yang berisi daftar pengguna yang diikuti.
     */
    @GET("users/{username}/following")
    fun getUserFollowing(@Path("username") username: String): Call<List<ItemsItem>>
}
