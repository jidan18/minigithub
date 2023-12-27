package com.example.minigithub.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteDao {

    // Memasukkan data favorit ke dalam database.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(note: FavoriteEntity)

    // Memperbarui data favorit yang sudah ada dalam database.
    @Update
    fun update(note: FavoriteEntity)

    // Menghapus data favorit dari database.
    @Delete
    fun delete(note: FavoriteEntity)

    // Mengambil semua pengguna favorit dari database dan mengurutkannya berdasarkan username.
    @Query("SELECT * from FavoriteEntity ORDER BY username ASC")
    fun getAllUser(): LiveData<List<FavoriteEntity>>

    // Mengambil data pengguna favorit berdasarkan username.
    @Query("SELECT * FROM FavoriteEntity WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteEntity>

    // Menambahkan data favorit dan memperbarui data favorit yang sudah ada.
    @Transaction
    fun insertAndUpdate(note: FavoriteEntity) {
        insert(note)
        update(note)
    }
}