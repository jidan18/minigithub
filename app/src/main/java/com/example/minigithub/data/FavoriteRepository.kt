package com.example.minigithub.data

import androidx.lifecycle.LiveData
import com.example.minigithub.remote.GithubApiService
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Kelas ini bertindak sebagai repository yang mengelola data favorit pengguna.
 * @param apiService Layanan API GitHub untuk mengambil data eksternal.
 * @param Dao Akses data favorit dari database lokal.
 */
class FavoriteRepository(apiService: GithubApiService, Dao: FavoriteDao) {
    private val mFavDao: FavoriteDao = Dao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    /**
     * Menyisipkan data favorit ke dalam database secara asynchronous.
     * @param favorite Data favorit yang akan disimpan dalam database.
     */
    fun insert(favorite: FavoriteEntity) {
        executorService.execute {
            mFavDao.insert(favorite)
            println("Data favorit berhasil dimasukkan ke dalam database!")
        }
    }

    /**
     * Mengambil data pengguna favorit berdasarkan username dan mengamati perubahan data secara real-time.
     * @param username Username pengguna yang akan dicari dalam database.
     * @return LiveData yang berisi data pengguna favorit.
     */
    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteEntity> {
        return mFavDao.getFavoriteUserByUsername(username)
            .also {
                println("Data pengguna favorit berhasil diambil dari database!")
            }
    }

    /**
     * Mengambil semua pengguna favorit dari database dan mengamati perubahan data secara real-time.
     * @return LiveData yang berisi daftar semua pengguna favorit.
     */
    fun getAllUser(): LiveData<List<FavoriteEntity>> {
        return mFavDao.getAllUser()
            .also {
                println("Data semua pengguna favorit berhasil diambil dari database!")
            }
    }

    /**
     * Menghapus data favorit dari database secara asynchronous.
     * @param favorite Data favorit yang akan dihapus dari database.
     */
    fun delete(favorite: FavoriteEntity) {
        executorService.execute {
            mFavDao.delete(favorite)
            println("Data favorit berhasil dihapus dari database!")
        }
    }
}
