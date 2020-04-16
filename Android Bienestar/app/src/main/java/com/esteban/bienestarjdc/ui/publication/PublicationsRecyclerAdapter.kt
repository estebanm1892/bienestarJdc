package com.esteban.bienestarjdc.ui.publication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.esteban.bienestarjdc.R
import com.esteban.bienestarjdc.data.Publication
import com.esteban.bienestarjdc.network.IMAGE_URL
import com.esteban.bienestarjdc.ui.publication.details.PublicationActivity
import kotlinx.android.synthetic.main.publications_list_item.view.*
import java.text.SimpleDateFormat
import java.util.*


class PublicationsRecyclerAdapter(private val context: Context, private val publications: List<Publication>) : RecyclerView.Adapter<PublicationsRecyclerAdapter.PublicationViewHolder>() {
    class PublicationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bind(publication: Publication, context: Context) {

            val sdfIn =
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val sdfOut =
                SimpleDateFormat("dd/MM/yyyy")
            val input = publication?.created_at
            val date: Date = sdfIn.parse(input)

            itemView.tittle.text = publication?.tittle
            itemView.created_at.text = sdfOut.format(date)
            itemView.area.text = "Area: " + publication?.area?.name

            val publicationImageURL = publication?.image
            Glide.with(context)
                .load(IMAGE_URL + publicationImageURL)
                .centerInside()
                .thumbnail(0.5f)
                .transition(withCrossFade())
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(itemView.image)


            itemView.setOnClickListener {
                val intent = Intent(context, PublicationActivity::class.java)
                intent.putExtra("id", publication?.id)
                context.startActivity(intent)

            }

            /*
            itemView.setOnClickListener {
                Toast.makeText(context, "publicación con id: " +publication?.tittle, Toast.LENGTH_LONG).show()
            }
             */

        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PublicationsRecyclerAdapter.PublicationViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.publications_list_item, parent, false)
        return PublicationViewHolder(v)
    }

    override fun getItemCount(): Int {
        return publications.size
    }

    override fun onBindViewHolder(holder: PublicationsRecyclerAdapter.PublicationViewHolder, position: Int) {
        return holder.bind(publications[position], context)

    }

}