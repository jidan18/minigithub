package com.example.minigithub.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.minigithub.ui.detail.DetailActivity
import com.example.minigithub.ItemsItem
import com.example.minigithub.databinding.UserListBinding // Ganti dengan package yang sesuai
import android.widget.Toast

class UserAdapter(private val listUser: List<ItemsItem>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    class ViewHolder(private val binding: UserListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(userItem: ItemsItem) {
            // Menggunakan Glide untuk memuat gambar dari URL ke ImageView.
            Glide.with(binding.root)
                .load(userItem.avatarUrl)
                .into(binding.imgAvatar)

            // Mengatur nilai TextView untuk menampilkan nama akun.
            binding.tvName.text = userItem.login

            // Mengatur event listener untuk item RecyclerView.
            binding.root.setOnClickListener {
                // Membuat Intent untuk memulai DetailActivity.
                val goToDetail = Intent(binding.root.context, DetailActivity::class.java)

                // Menambahkan data ke Intent.
                goToDetail.putExtra("Data", userItem.login)

                // Memulai DetailActivity.
                binding.root.context.startActivity(goToDetail)

                // Menampilkan toast.
                Toast.makeText(
                    binding.root.context,
                    "You choose ${userItem.login}",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = UserListBinding.inflate(inflater, viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(listUser[position])
    }

    override fun getItemCount() = listUser.size
}
