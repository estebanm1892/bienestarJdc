package com.esteban.bienestarjdc.ui.vresources

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.esteban.bienestarjdc.databinding.ActivityVirtualResourcesBinding
import com.esteban.bienestarjdc.network.MyApi
import com.esteban.bienestarjdc.repository.VirtualResourceRepository

class VirtualResourcesActivity : AppCompatActivity() {

    private lateinit var viewModel: VirtualResourcesViewModel
    private lateinit var binding: ActivityVirtualResourcesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVirtualResourcesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Recursos Virtuales"

        val apiService = MyApi.RetrofitObject()
        val virtualResourcesRepositoty = VirtualResourceRepository(apiService)
        val factory = VirtualResourcesViewModelFactory(virtualResourcesRepositoty)
        viewModel = ViewModelProvider(this, factory)[VirtualResourcesViewModel::class.java]

        binding.virtualResourcesRecyclerview.setHasFixedSize(true)
        binding.virtualResourcesRecyclerview.layoutManager = LinearLayoutManager(this)

        viewModel.virtualResources.observe(this) { virtualResources ->
            if (!virtualResources.isNullOrEmpty()){
                this?.let {
                    val adapter = VirtualResourcesRecyclerAdapter(this, virtualResources)
                    binding.virtualResourcesRecyclerview.adapter = adapter
                }
            }
            else{
                binding.noVirtualResources.visibility = View.VISIBLE
            }
        }

        intent.extras?.let {
            if (it.containsKey(PUB_ITEM_ID)){
                val id: Int = intent.getIntExtra(PUB_ITEM_ID, 0)
                viewModel.getVirtualResources(id)
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
