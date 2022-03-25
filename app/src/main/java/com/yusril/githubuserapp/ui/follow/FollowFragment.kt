package com.yusril.githubuserapp.ui.follow

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yusril.githubuserapp.data.model.User
import com.yusril.githubuserapp.databinding.FragmentFollowBinding
import com.yusril.githubuserapp.ui.detail.DetailActivity
import com.yusril.githubuserapp.ui.main.SearchAdapter
import com.yusril.githubuserapp.viewmodel.ViewModelFactory
import com.yusril.githubuserapp.vo.Resource
import com.yusril.githubuserapp.vo.Status

class FollowFragment : Fragment() {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: SearchAdapter
    private lateinit var viewModel: FollowViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)
        val user = activity?.intent?.getParcelableExtra<User>(DetailActivity.USER)

        initRecyclerView()
        initViewModel()
        if (index != null) {
            if (user != null) {
                setUserList(index, user)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUserList(index: Int, user: User) {
        when (index) {
            1 -> viewModel.getFollowers(user.login).observe(viewLifecycleOwner){
                populateFollow(it)
            }
            2 -> viewModel.getFollowing(user.login).observe(viewLifecycleOwner){
                populateFollow(it)
            }
        }
    }

    private fun populateFollow(it: Resource<ArrayList<User>>) {
        when(it.status) {
            Status.LOADING -> showLoading()
            Status.SUCCESS -> {
                it.data?.let { users -> adapter.setUser(users) }
                hideLoading()
            }
            Status.ERROR -> {
                hideLoading()
                Toast.makeText(activity, "Failure: ${it.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initViewModel(){
        val factory = ViewModelFactory.getInstance(requireActivity().application)
        viewModel = ViewModelProvider(this, factory)[FollowViewModel::class.java]
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initRecyclerView(){
        adapter = SearchAdapter()
        adapter.notifyDataSetChanged()
        val rv = binding.rvUsers
        rv.layoutManager = LinearLayoutManager(activity)
        rv.adapter = adapter
        rv.setHasFixedSize(false)
        adapter.setOnItemClickCallback(object : SearchAdapter.OnItemClickCallback{
            override fun onItemClicked(user: User) {
                DetailActivity.start(activity, user)
            }
        })
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

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"

        fun newInstance(index: Int) = FollowFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_SECTION_NUMBER, index)
            }
        }
    }
}