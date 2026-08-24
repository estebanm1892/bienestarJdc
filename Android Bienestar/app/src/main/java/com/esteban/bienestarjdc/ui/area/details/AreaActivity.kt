package com.esteban.bienestarjdc.ui.area.details

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.esteban.bienestarjdc.R
import com.esteban.bienestarjdc.databinding.ActivityAreaBinding
import com.esteban.bienestarjdc.network.IMAGE_URL
import com.esteban.bienestarjdc.network.MyApi
import com.esteban.bienestarjdc.repository.AreaRepository
import com.esteban.bienestarjdc.ui.activity.AreaActivitiesActivity
import com.esteban.bienestarjdc.ui.area.AreaViewModel
import com.esteban.bienestarjdc.ui.area.AreaViewModelFactory

class AreaActivity : AppCompatActivity() {

    private lateinit var viewModel: AreaViewModel
    private lateinit var binding: ActivityAreaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAreaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*
        inicializar
         */

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val apiService = MyApi.RetrofitObject()
        val areaRepository = AreaRepository(apiService)
        val factory = AreaViewModelFactory(areaRepository)
        viewModel = ViewModelProvider(this, factory)[AreaViewModel::class.java]

        binding.areaPublications.setHasFixedSize(true)
        binding.areaPublications.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        viewModel.area.observe(this) { area ->
            supportActionBar?.title = area.name
            if (!area.publications.isNullOrEmpty()) {
                this?.let {
                    val adapter = AreaPublicationsRecyclerAdapter(this, area.publications)
                    binding.areaPublications.adapter = adapter
                }
            }else{
                println("no hay noticias por acá.")
            }

            val areaImageURL = IMAGE_URL + area.area_image
            Glide.with(this)
                .load(areaImageURL)
                .centerInside()
                .thumbnail(0.5f)
                .transition(DrawableTransitionOptions.withCrossFade())
                .placeholder(R.drawable.ic_launcher_foreground)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(binding.areaImage)

            binding.areaInfo.setOnClickListener {
                val intent = Intent(this, AreaInformationActivity::class.java)
                intent.putExtra("id", area.id)
                startActivity(intent)
            }

            binding.areaActivities.setOnClickListener {
                val intent = Intent(this, AreaActivitiesActivity::class.java)
                intent.putExtra("id", area.id)
                startActivity(intent)
            }

        }

        intent.extras?.let {
            if(it.containsKey(PUB_ITEM_ID)){
                val id: Int = intent.getIntExtra(PUB_ITEM_ID, 0)
                viewModel.getArea(id)
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
