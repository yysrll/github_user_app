package com.yusril.githubuserapp.ui.main

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yusril.githubuserapp.R
import com.yusril.githubuserapp.data.model.User
import com.yusril.githubuserapp.databinding.ActivityMainBinding
import com.yusril.githubuserapp.vo.Status

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: SearchAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
        initViewModel()
        hideLoading()

    }

    private fun initViewModel(){
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initRecyclerView(){
        adapter = SearchAdapter()
        adapter.notifyDataSetChanged()
        val rv = binding.rvUsers
        rv.layoutManager = LinearLayoutManager(this@MainActivity)
        rv.adapter = adapter
        rv.setHasFixedSize(false)
        adapter.setOnItemClickCallback(object : SearchAdapter.OnItemClickCallback{
            override fun onItemClicked(user: User) {
                Toast.makeText(this@MainActivity, user.login, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        return super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.option_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(this@MainActivity, query, Toast.LENGTH_SHORT).show()
                if (query != null) {
                    if (query.isNotEmpty()) {
                        showLoading()
                        viewModel.setSearchResult(query).observe(this@MainActivity){
                            when(it.status){
                                Status.LOADING -> showLoading()
                                Status.SUCCESS -> {
                                    it.data?.let { users -> adapter.setUser(users) }
                                    hideLoading()
                                }
                                Status.ERROR -> {
                                    hideLoading()
                                    Toast.makeText(this@MainActivity,
                                        "Failure: ${it.message}", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        return true
    }

    private fun showLoading(){
        binding.apply {
            rvUsers.visibility = View.GONE
            shimmerLayout.visibility = View.VISIBLE
            shimmerLayout.showShimmer(true)
        }
    }

    private fun hideLoading(){
        binding.apply {
            shimmerLayout.stopShimmer()
            rvUsers.visibility = View.VISIBLE
            shimmerLayout.visibility = View.GONE
        }
    }
}