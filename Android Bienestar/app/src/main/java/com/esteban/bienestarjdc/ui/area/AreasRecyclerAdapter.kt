package com.esteban.bienestarjdc.ui.area

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.esteban.bienestarjdc.R
import com.esteban.bienestarjdc.data.Area

import com.esteban.bienestarjdc.databinding.AreasListItemBinding
import com.esteban.bienestarjdc.network.IMAGE_URL
import com.esteban.bienestarjdc.ui.area.details.AreaActivity


class AreasRecyclerAdapter(private val context: Context,
                           private val areas: List<Area>) : RecyclerView.Adapter<AreasRecyclerAdapter.AreaViewHolder>() {

     class AreaViewHolder(private val binding: AreasListItemBinding) : RecyclerView.ViewHolder(binding.root) {

          fun bind(area: Area?, context: Context) {
              binding.name.text = area?.name
              val areaImageURL = area?.area_image
              Glide.with(context)
                 .load(IMAGE_URL + areaImageURL)
                 .centerInside()
                 .thumbnail(0.5f)
                 .transition(withCrossFade())
                  .centerCrop()
                  .placeholder(R.drawable.ic_launcher_foreground)
                  .diskCacheStrategy(DiskCacheStrategy.ALL)
                  .into(binding.areaImage)

              binding.root.setOnClickListener {
                  val intent = Intent(context, AreaActivity::class.java)
                  intent.putExtra("id", area?.id)
                 context.startActivity(intent)

             }


         }

     }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AreaViewHolder {
        val binding = AreasListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AreaViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return areas.size
    }

    override fun onBindViewHolder(holder: AreaViewHolder, position: Int) {
        return holder.bind(areas[position], context)

    }


}
