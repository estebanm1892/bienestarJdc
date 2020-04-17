package com.esteban.bienestarjdc.ui.area.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.esteban.bienestarjdc.R
import com.esteban.bienestarjdc.network.IMAGE_URL
import com.esteban.bienestarjdc.network.MyApi
import com.esteban.bienestarjdc.repository.AreaRepository
import com.esteban.bienestarjdc.ui.area.AreaViewModel
import com.esteban.bienestarjdc.ui.area.AreaViewModelFactory
import kotlinx.android.synthetic.main.activity_area.*

class AreaActivity : AppCompatActivity() {

    private lateinit var viewModel: AreaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_area)

        /*
        inicializar
         */

        val apiService = MyApi.RetrofitObject()
        val areaRepository = AreaRepository(apiService)
        val factory = AreaViewModelFactory(areaRepository)
        viewModel = ViewModelProviders.of(this, factory).get(AreaViewModel::class.java)

        area_publications.setHasFixedSize(true)
        area_publications.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        viewModel.area.observe(this, Observer { area ->
            name.setText(area.name)



            if (!area.publications.isNullOrEmpty()) {
                this?.let {
                    val adapter = AreaPublicationsRecyclerAdapter(this, area.publications)
                    area_publications.adapter = adapter
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
                .into(area_image)
        })

        intent.extras?.let {
            if(it.containsKey(PUB_ITEM_ID)){
                val id: Int = intent.getIntExtra(PUB_ITEM_ID, 0)
                viewModel.getArea(id)
            }
        }
    }
    companion object {
        const val PUB_ITEM_ID = "id"
    }
}
