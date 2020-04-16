package com.esteban.bienestarjdc.ui.area.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.get
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.esteban.bienestarjdc.R
import com.esteban.bienestarjdc.data.Area
import com.esteban.bienestarjdc.data.Publication
import com.esteban.bienestarjdc.network.IMAGE_URL
import com.esteban.bienestarjdc.network.MyApi
import com.esteban.bienestarjdc.repository.AreaRepository
import com.esteban.bienestarjdc.ui.area.AreaViewModel
import com.esteban.bienestarjdc.ui.area.AreaViewModelFactory
import com.esteban.bienestarjdc.ui.publication.PublicationsRecyclerAdapter
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_area.*
import kotlinx.android.synthetic.main.activity_area.view.*
import kotlinx.android.synthetic.main.fragment_publications.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AreaActivity : AppCompatActivity() {

    private lateinit var viewModel: AreaViewModel
    private lateinit var compositeDisposable: CompositeDisposable
    lateinit var recyclerView: RecyclerView
    private lateinit var publicationRecyclerView: PublicationsRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_area)

        val bundle: Bundle? = intent.extras

        if(bundle?.containsKey(PUB_ITEM_ID)!!){
            val id: Int = intent.getIntExtra(PUB_ITEM_ID, 0)
            loadDetails(id)
        }

    }

    private fun loadDetails(id:Int){
        val apiService = MyApi()
        val areaRepository = AreaRepository(apiService)
        val factory = AreaViewModelFactory(areaRepository)
        viewModel = ViewModelProviders.of(this, factory).get(AreaViewModel::class.java)

        val requestCall: Call<List<Area>> = apiService.getArea(id)

        requestCall.enqueue(object : Callback<List<Area>>{
            override fun onFailure(call: Call<List<Area>>, t: Throwable) {
                Toast.makeText(
                    this@AreaActivity,
                    "Algo fallo al traer la información." + t.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onResponse(call: Call<List<Area>>, response: Response<List<Area>>) {
                if (response.isSuccessful){
                    val area = response.body()
                    area?.let {
                        name.setText(area.get(0).name)
                        /*
                        if (area.get(0).publications.get(0).tittle === null || area.size == 0){
                            println("no hay noticias")
                        }else{
                            println(area.get(0).publications.get(0).tittle)
                        }
                         */

                        val areaImageURL = IMAGE_URL + area.get(0).area_image
                        Glide.with(this@AreaActivity)
                            .load(areaImageURL)
                            .into(area_image)
                        /*
                        recyclerView = findViewById(R.id.publications_recylerview)
                        publicationRecyclerView = PublicationsRecyclerAdapter(this@AreaActivity, area.get(0).publications)
                        recyclerView.layoutManager = LinearLayoutManager(this@AreaActivity)
                        recyclerView.adapter = publicationRecyclerView
                         */


                    }
                }else {
                    Toast.makeText(this@AreaActivity, "Algo fallo al traer la información.", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }


    companion object {
        const val PUB_ITEM_ID = "id"
    }
}
