package com.esteban.bienestarjdc.ui.normative

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
import com.esteban.bienestarjdc.repository.NormativeRepository
import kotlinx.android.synthetic.main.fragment_normatives.*

/**
 * A simple [Fragment] subclass.
 */
class NormativesFragment : Fragment() {

    private lateinit var viewModel: NormativeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return container?.inflate(R.layout.fragment_normatives)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val apiService = MyApi()
        val normativeRepository = NormativeRepository(apiService)
        val factory = NormativeModelFactory(normativeRepository)
        viewModel = ViewModelProviders.of(this, factory).get(NormativeViewModel::class.java)

        normatives_recylerview.setHasFixedSize(true)
        normatives_recylerview.layoutManager = LinearLayoutManager(context)

        viewModel.normatives.observe(viewLifecycleOwner, Observer { normatives ->
            if (!normatives.isNullOrEmpty()){
                context?.let {
                    val adapter = NormativesRecyclerAdapter(it, normatives)
                    normatives_recylerview.adapter = adapter
                }
            }
        })

        viewModel.getNormatives()
    }

}
