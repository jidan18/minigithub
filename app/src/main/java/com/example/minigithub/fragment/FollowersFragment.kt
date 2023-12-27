package com.example.minigithub.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.minigithub.ItemsItem
import com.example.minigithub.adapter.UserAdapter
import com.example.minigithub.databinding.FragmentFollowersBinding
import com.example.minigithub.remote.ApiConfig
import com.example.minigithub.ui.detail.DetailActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersFragment : Fragment() {

    private lateinit var binding: FragmentFollowersBinding
    private var position: Int? = null
    private var username: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }

        setupRecyclerView()
        fetchData()
    }

    private fun setupRecyclerView() {
        binding.rvFollowers.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun fetchData() {
        showLoading(true)

        val client = position?.let {
            if (it == 1) {
                ApiConfig.getApiService().getUserFollowers(username.orEmpty())
            } else {
                ApiConfig.getApiService().getUserFollowing(username.orEmpty())
            }
        }

        client?.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(call: Call<List<ItemsItem>>, response: Response<List<ItemsItem>>) {
                showLoading(false)
                if (response.isSuccessful) {
                    response.body()?.let { displayFollowers(it) }
                } else {
                    showError("Failed: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                showLoading(false)
                showError("Error: ${t.message}")
            }
        })
    }

    private fun displayFollowers(followersList: List<ItemsItem>) {
        val adapter = UserAdapter(followersList)
        binding.rvFollowers.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar3.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showError(message: String) {
        Log.e(DetailActivity.TAG, message)
    }

    companion object {
        const val ARG_POSITION = "section_number"
        const val ARG_USERNAME = "name"
    }
}
