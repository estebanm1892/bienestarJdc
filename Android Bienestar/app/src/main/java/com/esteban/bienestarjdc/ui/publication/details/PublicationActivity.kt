package com.esteban.bienestarjdc.ui.publication.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.esteban.bienestarjdc.R
import com.esteban.bienestarjdc.network.IMAGE_URL
import com.esteban.bienestarjdc.network.MyApi
import com.esteban.bienestarjdc.repository.PublicationRepository
import com.esteban.bienestarjdc.ui.publication.PublicationViewModel
import com.esteban.bienestarjdc.ui.publication.PublicationViewModelFactory
import kotlinx.android.synthetic.main.activity_publication.*
import java.text.SimpleDateFormat
import java.util.*

class PublicationActivity : AppCompatActivity() {

    private lateinit var viewModel: PublicationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publication)

        val apiService = MyApi.RetrofitObject()
        val publicationRepository = PublicationRepository(apiService)
        val factory = PublicationViewModelFactory(publicationRepository)
        viewModel = ViewModelProviders.of(this, factory).get(PublicationViewModel::class.java)

        viewModel.publication.observe(this, Observer { publication ->
            val sdfIn =
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val sdfOut =
                SimpleDateFormat("dd/MM/yyyy")
            val input = publication.created_at
            val date: Date = sdfIn.parse(input)

            tittle.setText(publication.tittle)
            created_at.setText(sdfOut.format(date))
            content.setText("Area: " + publication.area?.name)
            content.setText(Html.fromHtml(publication.content))

            val publicationImageURL = IMAGE_URL + publication.image
            Glide.with(this)
                .load(publicationImageURL)
                .centerInside()
                .thumbnail(0.5f)
                .transition(DrawableTransitionOptions.withCrossFade())
                .placeholder(R.drawable.ic_launcher_foreground)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(image)

        })

        intent.extras?.let {
            if (it.containsKey(PUB_ITEM_ID)){
                val id: Int = intent.getIntExtra(PUB_ITEM_ID, 0)
                viewModel.getPublication(id)
            }
        }

    }

    companion object {
        const val PUB_ITEM_ID = "id"
    }



}




