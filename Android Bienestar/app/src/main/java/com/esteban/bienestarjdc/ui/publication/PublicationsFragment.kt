package com.esteban.bienestarjdc.ui.publication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.esteban.bienestarjdc.R
import com.esteban.bienestarjdc.extension.inflate
import com.esteban.bienestarjdc.network.MyApi
import com.esteban.bienestarjdc.repository.PublicationRepository
import kotlinx.android.synthetic.main.fragment_publications.*

/**
 * A simple [Fragment] subclass.
 */
class PublicationsFragment : Fragment() {

    private lateinit var viewModel: PublicationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return container?.inflate(R.layout.fragment_publications)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val apiService = MyApi()
        val publicationRepository = PublicationRepository(apiService)
        val factory = PublicationViewModelFactory(publicationRepository)
        viewModel = ViewModelProviders.of(this, factory).get(PublicationViewModel::class.java)

        publications_recylerview.setHasFixedSize(true)
        publications_recylerview.layoutManager = LinearLayoutManager(context)

        viewModel.publications.observe(viewLifecycleOwner, Observer { publications ->
            if (!publications.isNullOrEmpty()) {
                context?.let {
                    val adapter = PublicationsRecyclerAdapter(it, publications)
                    publications_recylerview.adapter = adapter
                }
            }
        })

        viewModel.getPublications()

    }

}
