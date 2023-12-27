package com.example.minigithub.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// Menandakan bahwa kelas ini adalah entitas yang akan disimpan dalam database.
@Entity
class FavoriteEntity(
    // Menandakan bahwa field `username` adalah kunci utama (primary key) dan tidak akan di-generate otomatis.
    @PrimaryKey(autoGenerate = false)
    var username: String = "", // Nilai default untuk username
    var avatarUrl: String // Field yang akan disimpan dalam database
)
