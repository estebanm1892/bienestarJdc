package com.esteban.bienestarjdc.ui.publication.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.esteban.bienestarjdc.R
import com.esteban.bienestarjdc.databinding.ActivityPublicationBinding
import com.esteban.bienestarjdc.network.IMAGE_URL
import com.esteban.bienestarjdc.network.MyApi
import com.esteban.bienestarjdc.repository.PublicationRepository
import com.esteban.bienestarjdc.ui.publication.PublicationViewModel
import com.esteban.bienestarjdc.ui.publication.PublicationViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

class PublicationActivity : AppCompatActivity() {

    private lateinit var viewModel: PublicationViewModel
    private lateinit var binding: ActivityPublicationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPublicationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Noticias"

        val apiService = MyApi.RetrofitObject()
        val publicationRepository = PublicationRepository(apiService)
        val factory = PublicationViewModelFactory(publicationRepository)
        viewModel = ViewModelProvider(this, factory)[PublicationViewModel::class.java]

        viewModel.publication.observe(this) { publication ->
            val sdfIn =
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val sdfOut =
                SimpleDateFormat("dd/MM/yyyy")
            val input = publication.created_at
            val date: Date = sdfIn.parse(input)

            binding.tittle.setText(publication.tittle)
            binding.createdAt.setText(sdfOut.format(date))
            binding.area.setText("Área: " + publication.area?.name)
            binding.content.setText(Html.fromHtml(publication.content))

            val publicationImageURL = IMAGE_URL + publication.image
            Glide.with(this)
                .load(publicationImageURL)
                .centerInside()
                .thumbnail(0.5f)
                .transition(DrawableTransitionOptions.withCrossFade())
                .placeholder(R.drawable.ic_launcher_foreground)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(binding.image)

        }

        intent.extras?.let {
            if (it.containsKey(PUB_ITEM_ID)){
                val id: Int = intent.getIntExtra(PUB_ITEM_ID, 0)
                viewModel.getPublication(id)
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        const val PUB_ITEM_ID = "id"
    }
}




