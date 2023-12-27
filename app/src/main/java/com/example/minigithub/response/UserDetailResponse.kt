package com.example.minigithub.response

import com.google.gson.annotations.SerializedName

data class UserDetailResponse(
    @SerializedName("login") val login: String, // Nama pengguna GitHub.
    @SerializedName("id") val id: Int, // ID pengguna GitHub.
    @SerializedName("avatar_url") val avatarUrl: String, // URL avatar pengguna.
    @SerializedName("name") val name: String?, // Nama lengkap pengguna (jika ada).
    @SerializedName("location") val location: String?, // Lokasi pengguna (jika ada).
    @SerializedName("email") val email: String?, // Alamat email pengguna (jika ada).
    @SerializedName("bio") val bio: String?, // Bio pengguna (jika ada).
    @SerializedName("public_repos") val publicRepos: Int, // Jumlah repositori publik pengguna.
    @SerializedName("followers") val followers: Int, // Jumlah pengikut pengguna.
    @SerializedName("following") val following: Int, // Jumlah pengguna yang diikuti oleh pengguna.
    @SerializedName("html_url") val htmlUrl: String // URL profil HTML pengguna di GitHub.
)
