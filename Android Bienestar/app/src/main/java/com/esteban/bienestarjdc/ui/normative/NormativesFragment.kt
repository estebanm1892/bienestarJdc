package com.esteban.bienestarjdc.ui.normative

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.esteban.bienestarjdc.R
import com.esteban.bienestarjdc.network.MyApi
import com.esteban.bienestarjdc.repository.NormativeRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_normatives.*

/**
 * A simple [Fragment] subclass.
 */
class NormativesFragment : Fragment() {

    private lateinit var viewModel: NormativeViewModel
    private lateinit var compositeDisposable: CompositeDisposable

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_normatives, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val apiService = MyApi()
        val normativeRepository = NormativeRepository(apiService)
        val factory = NormativeModelFactory(normativeRepository)
        compositeDisposable = CompositeDisposable()
        viewModel = ViewModelProviders.of(this, factory).get(NormativeViewModel::class.java)

        normatives_recylerview.setHasFixedSize(true)
        normatives_recylerview.layoutManager = LinearLayoutManager(context)

        compositeDisposable.add(
            viewModel.getNormatives()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { normatives ->
                   val adapter = NormativesRecyclerAdapter(context!!, normatives)
                    normatives_recylerview.adapter = adapter
                }
        )

    }

}
