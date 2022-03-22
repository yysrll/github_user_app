package com.yusril.githubuserapp.ui.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.yusril.githubuserapp.R
import com.yusril.githubuserapp.data.model.User
import com.yusril.githubuserapp.data.model.UserDetail
import com.yusril.githubuserapp.databinding.ActivityDetailBinding
import com.yusril.githubuserapp.ui.follow.SectionPagerAdapter
import com.yusril.githubuserapp.vo.Status

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getParcelableExtra<User>(USER)

        val sectionPagerAdapter = SectionPagerAdapter(this)
        val viewPager = binding.viewPager
        viewPager.adapter = sectionPagerAdapter
        val tabs = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f

        initViewModel()

        user?.let { u ->
            viewModel.getUser(u.login).observe(this){
                when(it.status){
                    Status.LOADING -> showLoading()
                    Status.SUCCESS -> {
                        it.data?.let { users -> populateDetail(users) }
                        hideLoading()
                    }
                    Status.ERROR -> {
                        hideLoading()
                        Toast.makeText(this,
                            "Failure: ${it.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun populateDetail(data: UserDetail) {
        Glide.with(this)
            .load(data.avatar_url)
            .circleCrop()
            .into(binding.header.imgAvatar)
        binding.apply {
            header.username.text = StringBuilder("@").append(data.login)
            header.company.text = data.company
            header.location.text = data.location
            header.totalRepo.text = data.repos.toString()
            header.totalFollowers.text = data.followers.toString()
            header.totalFollowing.text = data.following.toString()
        }
    }

    private fun initViewModel(){
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailViewModel::class.java]
    }


    private fun showLoading(){
        binding.apply {
            header.root.visibility = View.GONE
            tabs.visibility = View.GONE
            viewPager.visibility = View.GONE
            shimmerLayout.visibility = View.VISIBLE
            shimmerLayout.showShimmer(true)
        }
    }

    private fun hideLoading(){
        binding.apply {
            shimmerLayout.stopShimmer()
            header.root.visibility = View.VISIBLE
            tabs.visibility = View.VISIBLE
            viewPager.visibility = View.VISIBLE
            shimmerLayout.visibility = View.GONE
        }
    }

    companion object {
        const val USER = "user"

        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )

        fun start(context: Activity?, user: User) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(USER, user)
            context?.startActivity(intent)
        }
    }
}