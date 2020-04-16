package com.esteban.bienestarjdc.ui.publication.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.esteban.bienestarjdc.R
import com.esteban.bienestarjdc.data.Publication
import com.esteban.bienestarjdc.network.IMAGE_URL
import com.esteban.bienestarjdc.network.MyApi
import com.esteban.bienestarjdc.repository.PublicationRepository
import com.esteban.bienestarjdc.ui.publication.PublicationModelFactory
import com.esteban.bienestarjdc.ui.publication.PublicationViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_publication.*
import kotlinx.android.synthetic.main.publications_list_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class PublicationActivity : AppCompatActivity() {

    private lateinit var viewModel: PublicationViewModel
    private lateinit var compositeDisposable: CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publication)

        val bundle: Bundle? = intent.extras

        if (bundle?.containsKey(PUB_ITEM_ID)!!){
            val id: Int = intent.getIntExtra(PUB_ITEM_ID, 0)
            loadDetails(id)
        }

    }

    private fun loadDetails(id:Int){
        val apiService = MyApi()
        val publicationRepository = PublicationRepository(apiService)
        val factory = PublicationModelFactory(publicationRepository)
        viewModel = ViewModelProviders.of(this, factory).get(PublicationViewModel::class.java)

        val requestCall: Call<List<Publication>> = apiService.getPublication(id)

        requestCall.enqueue(object : Callback<List<Publication>>{
            override fun onFailure(call: Call<List<Publication>>, t: Throwable) {
                Toast.makeText(
                    this@PublicationActivity,
                    "Algo fallo al traer la información." + t.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onResponse(
                call: Call<List<Publication>>,
                response: Response<List<Publication>>
            ) {
                if (response.isSuccessful){
                    val publication = response.body()
                    publication?.let {

                        val sdfIn =
                            SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                        val sdfOut =
                            SimpleDateFormat("dd/MM/yyyy")
                        val input = publication.get(0).created_at
                        val date: Date = sdfIn.parse(input)

                        created_at.setText(sdfOut.format(date))
                        tittle.setText(publication.get(0).tittle)
                        content.setText(Html.fromHtml(publication.get(0).content))

                        area.setText("Area: " + publication.get(0).area.name)

                        val publicationImageURL = IMAGE_URL + publication.get(0).image
                        Glide.with(this@PublicationActivity)
                            .load(publicationImageURL)
                            .into(image)
                    }
                } else{
                    Toast.makeText(this@PublicationActivity, "Algo fallo al traer la información.", Toast.LENGTH_SHORT)
                        .show()
                }
            }


        })

    }

    companion object {
        const val PUB_ITEM_ID = "id"
    }

}
