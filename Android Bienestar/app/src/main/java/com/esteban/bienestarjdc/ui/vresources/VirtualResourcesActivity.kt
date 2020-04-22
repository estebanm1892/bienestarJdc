package com.esteban.bienestarjdc.ui.vresources

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.esteban.bienestarjdc.R
import com.esteban.bienestarjdc.network.MyApi
import com.esteban.bienestarjdc.repository.VirtualResourceRepository
import kotlinx.android.synthetic.main.activity_virtual_resources.*

class VirtualResourcesActivity : AppCompatActivity() {

    private lateinit var viewModel: VirtualResourcesViewModel
    private var emptyData: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_virtual_resources)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Recursos Virtuales"

        val apiService = MyApi.RetrofitObject()
        val virtualResourcesRepositoty = VirtualResourceRepository(apiService)
        val factory = VirtualResourcesViewModelFactory(virtualResourcesRepositoty)
        viewModel = ViewModelProviders.of(this, factory).get(VirtualResourcesViewModel::class.java)

        virtual_resources_recyclerview.setHasFixedSize(true)
        virtual_resources_recyclerview.layoutManager = LinearLayoutManager(this)

        emptyData = findViewById(R.id.no_virtual_resources) as TextView

        viewModel.virtualResources.observe(this, Observer { virtualResources ->
            if (!virtualResources.isNullOrEmpty()){
                this?.let {
                    val adapter = VirtualResourcesRecyclerAdapter(this, virtualResources)
                    virtual_resources_recyclerview.adapter = adapter
                }
            }
            else{
                emptyData!!.setVisibility(View.VISIBLE)
            }
        })

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
