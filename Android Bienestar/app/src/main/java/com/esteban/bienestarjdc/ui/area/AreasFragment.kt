package com.esteban.bienestarjdc.ui.area

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
import com.esteban.bienestarjdc.repository.AreaRepository
import kotlinx.android.synthetic.main.fragment_areas.*

/**
 * A simple [Fragment] subclass.
 */
class AreasFragment : Fragment() {

    private lateinit var viewModel: AreaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return container?.inflate(R.layout.fragment_areas)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val apiService = MyApi.RetrofitObject()
        val areaRepository = AreaRepository(apiService)
        val factory = AreaViewModelFactory(areaRepository)
        viewModel = ViewModelProviders.of(this, factory).get(AreaViewModel::class.java)

        areas_recylerview.setHasFixedSize(true)
        areas_recylerview.layoutManager = LinearLayoutManager(context)

        viewModel.areas.observe(viewLifecycleOwner, Observer { areas ->
            if (!areas.isNullOrEmpty()) {
                context?.let {
                    val adapter = AreasRecyclerAdapter(it, areas)
                    areas_recylerview.adapter = adapter
                }
            }
        })

        viewModel.getAreas()
    }

}
