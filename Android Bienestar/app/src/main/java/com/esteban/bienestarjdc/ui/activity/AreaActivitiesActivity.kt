package com.esteban.bienestarjdc.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.esteban.bienestarjdc.databinding.ActivityAreaActivitiesBinding
import com.esteban.bienestarjdc.network.MyApi
import com.esteban.bienestarjdc.repository.ActivityRepository

class AreaActivitiesActivity : AppCompatActivity() {

    private lateinit var viewModel: AreaActivityViewModel
    private lateinit var binding: ActivityAreaActivitiesBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAreaActivitiesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Actividades"

        val apiService = MyApi.RetrofitObject()
        val activitiesRepository = ActivityRepository(apiService)
        val factory = AreaActivityViewModelFactory(activitiesRepository)
        viewModel = ViewModelProvider(this, factory)[AreaActivityViewModel::class.java]

        binding.activitiesRecyclerview.setHasFixedSize(true)
        binding.activitiesRecyclerview.layoutManager = LinearLayoutManager(this)

        viewModel.areactivities.observe(this) { activities ->
            if (!activities.isNullOrEmpty()){
                this?.let {
                    val adapter = AreaActivitiesRecyclerAdapter(this, activities)
                    binding.activitiesRecyclerview.adapter = adapter
                }
            }else{
                binding.noActivities.visibility = View.VISIBLE
            }
        }

        intent.extras?.let {
            if (it.containsKey(PUB_ITEM_ID)){
                val id: Int = intent.getIntExtra(PUB_ITEM_ID, 0)
                viewModel.getActivities(id)
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
