package com.esteban.bienestarjdc.ui.vresources.details

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.esteban.bienestarjdc.R
import com.esteban.bienestarjdc.databinding.ActivityVirtualResourceBinding
import com.esteban.bienestarjdc.network.IMAGE_URL
import com.esteban.bienestarjdc.network.MyApi
import com.esteban.bienestarjdc.repository.VirtualResourceRepository
import com.esteban.bienestarjdc.ui.vresources.VirtualResourcesViewModel
import com.esteban.bienestarjdc.ui.vresources.VirtualResourcesViewModelFactory

class VirtualResourceActivity : AppCompatActivity() {

    private lateinit var viewModel: VirtualResourcesViewModel
    private lateinit var binding: ActivityVirtualResourceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVirtualResourceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Recursos Virtuales"

        val apiService = MyApi.RetrofitObject()
        val virtualResourceRepository = VirtualResourceRepository(apiService)
        val factory = VirtualResourcesViewModelFactory(virtualResourceRepository)
        viewModel = ViewModelProvider(this, factory)[VirtualResourcesViewModel::class.java]

        viewModel.virtualResource.observe(this) { virtualResource ->
            binding.tittle.setText(virtualResource.tittle)
            binding.description.setText(virtualResource.description)
            if (!virtualResource.embed_video.isNullOrEmpty()){
                binding.webviewPlayerView.settings.javaScriptEnabled = true
                binding.webviewPlayerView.loadUrl(virtualResource.embed_video)
            }else{
                binding.webviewPlayerView.visibility = View.GONE
                binding.noVideo.visibility = View.VISIBLE
            }
            if (!virtualResource.docs.isNullOrEmpty()){
                binding.docs.setOnClickListener {
                    val browserIntent =
                        Intent(Intent.ACTION_VIEW, Uri.parse(IMAGE_URL + virtualResource.docs ))
                    browserIntent.putExtra("id", virtualResource.docs)
                    startActivity(browserIntent)
                }
            }else{
                binding.docs.visibility = View.GONE
                binding.noDocument.visibility = View.VISIBLE
            }
            if (!virtualResource.image.isNullOrEmpty()){
                val resourceImageURL = IMAGE_URL + virtualResource.image
                Glide.with(this)
                    .load(resourceImageURL)
                    .centerInside()
                    .thumbnail(0.5f)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(binding.image)
            }else{
                binding.image.visibility = View.GONE
                binding.noImage.visibility = View.VISIBLE
            }

        }

        intent.extras?.let {
            if (it.containsKey(PUB_ITEM_ID)){
                val id: Int = intent.getIntExtra(PUB_ITEM_ID, 0)
                viewModel.getVirtualResource(id)
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
