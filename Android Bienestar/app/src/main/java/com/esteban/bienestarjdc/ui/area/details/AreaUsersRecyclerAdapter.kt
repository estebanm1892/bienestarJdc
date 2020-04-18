package com.esteban.bienestarjdc.ui.area.details

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.esteban.bienestarjdc.R
import com.esteban.bienestarjdc.data.User
import com.esteban.bienestarjdc.network.IMAGE_URL
import kotlinx.android.synthetic.main.area_users_lists_item.view.*

class AreaUsersRecyclerAdapter(private val context: Context, private val users: List<User>): RecyclerView.Adapter<AreaUsersRecyclerAdapter.UserViewHolder>() {
    class UserViewHolder(itemview: View): RecyclerView.ViewHolder(itemview){

        fun bind(user: User, context: Context) {
            itemView.name.text = user?.name

            val userImageURL = user?.profile_image
            Glide.with(context)
                .load(IMAGE_URL + userImageURL)
                .centerInside()
                .thumbnail(0.5f)
                .transition(DrawableTransitionOptions.withCrossFade())
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(itemView.profile_image)

        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AreaUsersRecyclerAdapter.UserViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.area_users_lists_item, parent, false)
        return  UserViewHolder(v)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: AreaUsersRecyclerAdapter.UserViewHolder, position: Int) {
        return holder.bind(users[position], context)
    }
}