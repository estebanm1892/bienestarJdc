package com.esteban.bienestarjdc.ui.normative

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.esteban.bienestarjdc.databinding.FragmentNormativesBinding
import com.esteban.bienestarjdc.network.MyApi
import com.esteban.bienestarjdc.repository.NormativeRepository

/**
 * A simple [Fragment] subclass.
 */
class NormativesFragment : Fragment() {

    private lateinit var viewModel: NormativeViewModel
    private var _binding: FragmentNormativesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNormativesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val apiService = MyApi()
        val normativeRepository = NormativeRepository(apiService)
        val factory = NormativeModelFactory(normativeRepository)
        viewModel = ViewModelProvider(this, factory)[NormativeViewModel::class.java]

        binding.normativesRecylerview.setHasFixedSize(true)
        binding.normativesRecylerview.layoutManager = LinearLayoutManager(context)

        viewModel.normatives.observe(viewLifecycleOwner) { normatives ->
            if (!normatives.isNullOrEmpty()){
                context?.let {
                    val adapter = NormativesRecyclerAdapter(it, normatives)
                    binding.normativesRecylerview.adapter = adapter
                }
            }
        }

        viewModel.getNormatives()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
