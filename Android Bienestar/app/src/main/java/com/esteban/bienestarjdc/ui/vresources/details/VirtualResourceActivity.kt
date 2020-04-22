package com.esteban.bienestarjdc.ui.vresources.details

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.esteban.bienestarjdc.R
import com.esteban.bienestarjdc.network.IMAGE_URL
import com.esteban.bienestarjdc.network.MyApi
import com.esteban.bienestarjdc.repository.VirtualResourceRepository
import com.esteban.bienestarjdc.ui.vresources.VirtualResourcesViewModel
import com.esteban.bienestarjdc.ui.vresources.VirtualResourcesViewModelFactory
import kotlinx.android.synthetic.main.activity_virtual_resource.*

class VirtualResourceActivity : AppCompatActivity() {

    private lateinit var viewModel: VirtualResourcesViewModel
    private var emptyVideo: TextView? = null
    private var emptyDoc: TextView? = null
    private var emptyImage: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_virtual_resource)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Recursos Virtuales"

        val apiService = MyApi.RetrofitObject()
        val virtualResourceRepository = VirtualResourceRepository(apiService)
        val factory = VirtualResourcesViewModelFactory(virtualResourceRepository)
        viewModel = ViewModelProviders.of(this, factory).get(VirtualResourcesViewModel::class.java)

        emptyVideo = findViewById(R.id.no_video) as TextView
        emptyDoc = findViewById(R.id.no_document) as TextView
        emptyImage = findViewById(R.id.no_image) as TextView

        viewModel.virtualResource.observe(this, Observer { virtualResource ->
            tittle.setText(virtualResource.tittle)
            description.setText(virtualResource.description)
            if (!virtualResource.embed_video.isNullOrEmpty()){
                webview_player_view.settings.javaScriptEnabled = true
                webview_player_view.loadUrl(virtualResource.embed_video)
            }else{
                webview_player_view!!.setVisibility(View.GONE)
                emptyVideo!!.setVisibility(View.VISIBLE)
            }
            if (!virtualResource.docs.isNullOrEmpty()){
                docs.setOnClickListener {
                    val browserIntent =
                        Intent(Intent.ACTION_VIEW, Uri.parse(IMAGE_URL + virtualResource.docs ))
                    browserIntent.putExtra("id", virtualResource.docs)
                    startActivity(browserIntent)
                }
            }else{
                docs!!.setVisibility(View.GONE)
                emptyDoc!!.setVisibility(View.VISIBLE)
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
                    .into(image)
            }else{
                image!!.setVisibility(View.GONE)
                emptyImage!!.setVisibility(View.VISIBLE)
            }

        })

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
