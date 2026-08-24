package com.esteban.bienestarjdc.ui.area.details

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.esteban.bienestarjdc.R
import com.esteban.bienestarjdc.data.User
import com.esteban.bienestarjdc.databinding.AreaUsersListsItemBinding
import com.esteban.bienestarjdc.network.IMAGE_URL

class AreaUsersRecyclerAdapter(private val context: Context, private val users: List<User>): RecyclerView.Adapter<AreaUsersRecyclerAdapter.UserViewHolder>() {
    class UserViewHolder(private val binding: AreaUsersListsItemBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(user: User, context: Context) {
            binding.name.text = user?.name

            val userImageURL = user?.profile_image
            Glide.with(context)
                .load(IMAGE_URL + userImageURL)
                .centerInside()
                .thumbnail(0.5f)
                .transition(DrawableTransitionOptions.withCrossFade())
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.profileImage)

        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AreaUsersRecyclerAdapter.UserViewHolder {
        val binding = AreaUsersListsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  UserViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: AreaUsersRecyclerAdapter.UserViewHolder, position: Int) {
        return holder.bind(users[position], context)
    }
}
