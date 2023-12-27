package com.example.minigithub.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.minigithub.fragment.FollowersFragment

// Kelas `SectionsPagerAdapter` adalah adapter untuk ViewPager2
// yang digunakan untuk menampilkan dua halaman `FollowersFragment`
class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    // Variabel ini menyimpan nama pengguna yang dipilih pengguna
    var selectedUsername: String = "github"

    // Objek companion adalah objek yang dapat diakses tanpa perlu membuat instance dari kelas
// Objek companion biasanya digunakan untuk menyimpan variabel atau fungsi yang bersifat statis
    companion object {
        private const val DEFAULT_NUM_PAGES = 2
    }

    // Metode ini digunakan untuk membuat instance dari fragment yang akan ditampilkan di ViewPager2
// Pada kode di atas, metode ini digunakan untuk membuat instance dari `FollowersFragment`
    override fun createFragment(position: Int): Fragment {
        return createFollowersFragment(position + 1)
    }

    // Metode ini digunakan untuk mengembalikan jumlah halaman yang akan ditampilkan di ViewPager2
// Pada kode di atas, metode ini mengembalikan konstanta `DEFAULT_NUM_PAGES`, yang berarti akan ada 2 halaman di ViewPager2
    override fun getItemCount(): Int {
        return DEFAULT_NUM_PAGES
    }

    // Metode ini digunakan untuk membuat instance dari `FollowersFragment` dan menambahkan argumen ke fragment tersebut
// Argumen yang ditambahkan adalah posisi halaman dan nama pengguna yang dipilih
    private fun createFollowersFragment(position: Int): Fragment {
        val fragment = FollowersFragment()
        fragment.arguments = createFragmentArguments(position)
        return fragment
    }

    // Metode ini digunakan untuk membuat instance dari `Bundle` dan menambahkan argumen ke `Bundle` tersebut
// Argumen yang ditambahkan adalah posisi halaman dan nama pengguna yang dipilih
    private fun createFragmentArguments(position: Int): Bundle {
        return Bundle().apply {
            putInt(FollowersFragment.ARG_POSITION, position)
            putString(FollowersFragment.ARG_USERNAME, selectedUsername)

        }
    }
}
