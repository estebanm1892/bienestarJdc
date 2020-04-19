package com.esteban.bienestarjdc.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.esteban.bienestarjdc.R
import com.esteban.bienestarjdc.network.MyApi
import com.esteban.bienestarjdc.repository.ActivityRepository
import com.esteban.bienestarjdc.repository.AreaRepository
import com.esteban.bienestarjdc.ui.area.AreaViewModelFactory
import kotlinx.android.synthetic.main.activities_list_item.*
import kotlinx.android.synthetic.main.activity_area_activities.*

class AreaActivitiesActivity : AppCompatActivity() {

    private lateinit var viewModel: AreaActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_area_activities)

        val apiService = MyApi.RetrofitObject()
        val activitiesRepository = ActivityRepository(apiService)
        val factory = AreaActivityViewModelFactory(activitiesRepository)
        viewModel = ViewModelProviders.of(this, factory).get(AreaActivityViewModel::class.java)

        activities_recyclerview.setHasFixedSize(true)
        activities_recyclerview.layoutManager = LinearLayoutManager(this)

        viewModel.areactivities.observe(this, Observer { activities ->
            if (!activities.isNullOrEmpty()){
                this?.let {
                    val adapter = AreaActivitiesRecyclerAdapter(this, activities)
                    activities_recyclerview.adapter = adapter
                }
            }

        })

        intent.extras?.let {
            if (it.containsKey(PUB_ITEM_ID)){
                val id: Int = intent.getIntExtra(PUB_ITEM_ID, 0)
                viewModel.getActivities(id)
            }
        }

    }
    companion object {
        const val PUB_ITEM_ID = "id"
    }
}
