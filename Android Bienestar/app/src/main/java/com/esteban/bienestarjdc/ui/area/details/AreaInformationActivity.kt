package com.esteban.bienestarjdc.ui.area.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
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
import kotlinx.android.synthetic.main.activity_area_information.*
import kotlinx.android.synthetic.main.activity_area_information.area_image

class AreaInformationActivity : AppCompatActivity() {

    private lateinit var viewModel: AreaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_area_information)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val apiService = MyApi.RetrofitObject()
        val areaRepository = AreaRepository(apiService)
        val factory = AreaViewModelFactory(areaRepository)
        viewModel = ViewModelProviders.of(this, factory).get(AreaViewModel::class.java)

        area_users.setHasFixedSize(true)
        area_users.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        viewModel.area.observe(this, Observer { area ->
            supportActionBar?.title = area.name
            area_presentation.setText(Html.fromHtml(area.area_presentation))
            objetive.setText(Html.fromHtml(area.objetive))
            programs.setText(Html.fromHtml(area.programs))

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

            if (!area.users.isNullOrEmpty()){
                this?.let {
                    val adapter = AreaUsersRecyclerAdapter(this, area.users)
                    area_users.adapter = adapter
                }
            }

        })

        intent.extras?.let {
            if (it.containsKey(PUB_ITEM_ID)){
                val id: Int = intent.getIntExtra(PUB_ITEM_ID, 0)
                viewModel.getAreaInformation(id)
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
