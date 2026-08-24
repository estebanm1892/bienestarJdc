package com.esteban.bienestarjdc.ui.area.details

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.esteban.bienestarjdc.R
import com.esteban.bienestarjdc.data.Publication
import com.esteban.bienestarjdc.databinding.AreaPublicationsListItemBinding
import com.esteban.bienestarjdc.network.IMAGE_URL
import com.esteban.bienestarjdc.ui.publication.details.PublicationActivity
import java.text.SimpleDateFormat
import java.util.*

class AreaPublicationsRecyclerAdapter(private val context: Context, private val publications: List<Publication>) : RecyclerView.Adapter<AreaPublicationsRecyclerAdapter.PublicationViewHolder>() {
    class PublicationViewHolder(private val binding: AreaPublicationsListItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(publication: Publication, context: Context) {
            val sdfIn =
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val sdfOut =
                SimpleDateFormat("dd/MM/yyyy")
            val input = publication?.created_at
            val date: Date = sdfIn.parse(input)
            binding.tittle.text = publication?.tittle
            binding.createdAt.text = sdfOut.format(date)

            val publicationImageURL = publication?.image
            Glide.with(context)
                .load(IMAGE_URL + publicationImageURL)
                .centerInside()
                .thumbnail(0.5f)
                .transition(DrawableTransitionOptions.withCrossFade())
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.image)


            binding.root.setOnClickListener {
                val intent = Intent(context, PublicationActivity::class.java)
                intent.putExtra("id", publication?.id)
                context.startActivity(intent)

            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AreaPublicationsRecyclerAdapter.PublicationViewHolder {
        val binding = AreaPublicationsListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PublicationViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return publications.size
    }

    override fun onBindViewHolder(
        holder: AreaPublicationsRecyclerAdapter.PublicationViewHolder,
        position: Int
    ) {
        return holder.bind(publications[position], context)
    }
}
