package com.example.minigithub.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

// Menentukan entitas yang akan disimpan dalam database dan versi database.
@Database(entities = [FavoriteEntity::class], version = 1, exportSchema = false)
abstract class dbFavoriteUser : RoomDatabase() {

    // Mendefinisikan DAO yang akan digunakan untuk mengakses data dalam database.
    abstract fun favDao(): FavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: dbFavoriteUser? = null

        // Fungsi untuk mendapatkan instance tunggal dari database.
        @JvmStatic
        fun getDatabase(context: Context): dbFavoriteUser {
            if (INSTANCE == null) {
                synchronized(dbFavoriteUser::class.java) {
                    // Membuat atau membuka database Room.
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        dbFavoriteUser::class.java,
                        "app_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE as dbFavoriteUser
        }
    }
}

// Migrasi dari versi 1 ke versi 2.
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE user ADD COLUMN email TEXT")
        // Kode untuk menmigrasi data dari versi 1 ke versi 2.
    }
}