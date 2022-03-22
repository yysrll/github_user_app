package com.yusril.githubuserapp.ui.follow

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yusril.githubuserapp.R
import com.yusril.githubuserapp.data.model.User
import com.yusril.githubuserapp.databinding.FragmentFollowBinding
import com.yusril.githubuserapp.ui.detail.DetailActivity
import com.yusril.githubuserapp.ui.main.MainViewModel
import com.yusril.githubuserapp.ui.main.SearchAdapter

class FollowFragment : Fragment() {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!
//    private lateinit var binding: FragmentFollowBinding
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
                it.data?.let { users -> adapter.setUser(users) }
            }
            2 -> viewModel.getFollowing(user.login).observe(viewLifecycleOwner){
                it.data?.let { users -> adapter.setUser(users) }
            }
        }
    }

    private fun initViewModel(){
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[FollowViewModel::class.java]
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

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"

        fun newInstance(index: Int) = FollowFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_SECTION_NUMBER, index)
            }
        }
    }
}