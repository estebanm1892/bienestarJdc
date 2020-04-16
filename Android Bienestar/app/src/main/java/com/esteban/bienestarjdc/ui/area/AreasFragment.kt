package com.esteban.bienestarjdc.ui.area

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.esteban.bienestarjdc.R
import com.esteban.bienestarjdc.network.MyApi
import com.esteban.bienestarjdc.repository.AreaRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_areas.*

/**
 * A simple [Fragment] subclass.
 */
class AreasFragment : Fragment() {

    private lateinit var viewModel: AreaViewModel
    private lateinit var compositeDisposable: CompositeDisposable

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_areas, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val apiService = MyApi.RetrofitObject()
        val areaRepository = AreaRepository(apiService)
        val factory = AreaViewModelFactory(areaRepository)
        compositeDisposable = CompositeDisposable()
        viewModel = ViewModelProviders.of(this, factory).get(AreaViewModel::class.java)

        areas_recylerview.setHasFixedSize(true)
        areas_recylerview.layoutManager = LinearLayoutManager(context)

        compositeDisposable.add(
           viewModel.getAreas()
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe { areas ->
                   val adapter = AreasRecyclerAdapter(context!!, areas)
                   areas_recylerview.adapter = adapter
               }
        )

    }

}
