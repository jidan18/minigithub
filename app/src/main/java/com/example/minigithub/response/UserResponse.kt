package com.example.minigithub

import com.google.gson.annotations.SerializedName

/**
 * Data class yang mewakili respons JSON dari hasil pencarian pengguna GitHub.
 *
 * @property totalCount Jumlah total hasil pencarian.
 * @property incompleteResults Menunjukkan apakah hasil pencarian tidak lengkap atau tidak.
 * @property items Daftar pengguna yang cocok dengan hasil pencarian.
 */
data class UserResponse(
	@SerializedName("total_count") val totalCount: Int,
	@SerializedName("incomplete_results") val incompleteResults: Boolean,
	@SerializedName("items") val items: List<ItemsItem>
)

/**
 * Data class yang mewakili item pengguna dalam hasil pencarian.
 *
 * @property login Nama pengguna GitHub.
 * @property avatarUrl URL avatar pengguna.
 */
data class ItemsItem(
	@SerializedName("login") val login: String,
	@SerializedName("avatar_url") val avatarUrl: String
)
