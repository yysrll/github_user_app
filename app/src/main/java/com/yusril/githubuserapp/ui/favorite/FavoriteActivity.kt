package com.yusril.githubuserapp.ui.favorite

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yusril.githubuserapp.R
import com.yusril.githubuserapp.data.model.User
import com.yusril.githubuserapp.databinding.ActivityFavoriteBinding
import com.yusril.githubuserapp.ui.detail.DetailActivity
import com.yusril.githubuserapp.ui.main.MainViewModel
import com.yusril.githubuserapp.ui.main.SearchAdapter
import com.yusril.githubuserapp.viewmodel.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var adapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initRecyclerView()
        initViewModel()

        viewModel.getAllFavorites().observe(this) {
            adapter.setUser(it)
        }

        binding.shimmerLayout.visibility = View.GONE
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun initViewModel(){
        val factory = ViewModelFactory.getInstance(application)
        viewModel = ViewModelProvider(this, factory)[FavoriteViewModel::class.java]
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initRecyclerView(){
        adapter = SearchAdapter()
        adapter.notifyDataSetChanged()
        val rv = binding.rvUsers
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter
        rv.setHasFixedSize(false)
        adapter.setOnItemClickCallback(object : SearchAdapter.OnItemClickCallback{
            override fun onItemClicked(user: User) {
                Toast.makeText(this@FavoriteActivity, user.login, Toast.LENGTH_SHORT).show()
                DetailActivity.start(this@FavoriteActivity, user)
            }
        })
    }

    companion object {
        fun start(context: Activity?) {
            val intent = Intent(context, FavoriteActivity::class.java)
            context?.startActivity(intent)
        }
    }
}