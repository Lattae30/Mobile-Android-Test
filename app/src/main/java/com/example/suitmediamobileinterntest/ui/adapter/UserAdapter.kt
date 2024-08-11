package com.example.suitmediamobileinterntest.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.suitmediamobileinterntest.databinding.UserItemListBinding
import com.example.suitmediamobileinterntest.retrofit.response.DataItem

class UserAdapter : PagingDataAdapter<DataItem, UserAdapter.UserViewHolder>(DIFF_CALLBACK){

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback (onItemClickedCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickedCallback
    }

    interface OnItemClickCallback{
        fun onItemClicked(data: DataItem)
    }

    inner class UserViewHolder(private val binding: UserItemListBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(users: DataItem){
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(users)
            }

            binding.apply {
                Glide.with(itemView)
                    .load(users.avatar.toString())
                    .skipMemoryCache(true)
                    .centerCrop()
                    .into(ivUserAvatar)
                tvFirstName.text = users.firstName
                tvLastName.text = users.lastName
                tvEmail.text = users.email
            }
        }
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val users = getItem(position)
        if(users != null){
            holder.bind(users)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = UserItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    companion object{
        val DIFF_CALLBACK = object  : DiffUtil.ItemCallback<DataItem>(){
            override fun areItemsTheSame(oldStory: DataItem, newStory: DataItem): Boolean {
                return oldStory == newStory
            }
            override fun areContentsTheSame(oldStory: DataItem, newStory: DataItem): Boolean {
                return oldStory == newStory
            }
        }
    }

}

