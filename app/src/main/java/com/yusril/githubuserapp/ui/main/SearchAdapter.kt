package com.yusril.githubuserapp.ui.main

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yusril.githubuserapp.data.model.User
import com.yusril.githubuserapp.databinding.UserRowItemBinding

class SearchAdapter: RecyclerView.Adapter<SearchAdapter.RecyclerViewHolder>() {

    private val listUsers =  ArrayList<User>()
    private var onItemClickCallback: OnItemClickCallback? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setUser(items: ArrayList<User>) {
        listUsers.clear()
        listUsers.addAll(items)
        this.notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class RecyclerViewHolder(private val binding: UserRowItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(user.avatar_url)
                    .into(userAvatar)
                userName.text = user.login
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val binding = UserRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bind(listUsers[position])
        holder.itemView.setOnClickListener { onItemClickCallback?.onItemClicked(listUsers[position]) }
    }

    override fun getItemCount(): Int = listUsers.size

    interface OnItemClickCallback {
        fun onItemClicked(user: User)
    }

}