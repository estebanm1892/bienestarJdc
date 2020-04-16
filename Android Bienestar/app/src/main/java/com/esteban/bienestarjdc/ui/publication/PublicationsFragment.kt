package com.esteban.bienestarjdc.ui.publication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.esteban.bienestarjdc.R
import com.esteban.bienestarjdc.network.MyApi
import com.esteban.bienestarjdc.repository.PublicationRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_publications.*

/**
 * A simple [Fragment] subclass.
 */
class PublicationsFragment : Fragment() {

    private lateinit var viewModel: PublicationViewModel
    private lateinit var compositeDisposable: CompositeDisposable

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_publications, container, false)

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val apiService = MyApi()
        val publicationRepository = PublicationRepository(apiService)
        val factory = PublicationModelFactory(publicationRepository)
        compositeDisposable = CompositeDisposable()
        viewModel = ViewModelProviders.of(this, factory).get(PublicationViewModel::class.java)

        publications_recylerview.setHasFixedSize(true)
        publications_recylerview.layoutManager = LinearLayoutManager(context)

        compositeDisposable.add(
            viewModel.getPublications()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { publications ->
                    val adapter = PublicationsRecyclerAdapter(context!!, publications )
                    publications_recylerview.adapter = adapter
                }
        )
    }

}
