package com.esteban.bienestarjdc.ui.area

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.esteban.bienestarjdc.databinding.FragmentAreasBinding
import com.esteban.bienestarjdc.network.MyApi
import com.esteban.bienestarjdc.repository.AreaRepository

/**
 * A simple [Fragment] subclass.
 */
class AreasFragment : Fragment() {

    private lateinit var viewModel: AreaViewModel
    private var _binding: FragmentAreasBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAreasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val apiService = MyApi.RetrofitObject()
        val areaRepository = AreaRepository(apiService)
        val factory = AreaViewModelFactory(areaRepository)
        viewModel = ViewModelProvider(this, factory)[AreaViewModel::class.java]

        binding.areasRecylerview.setHasFixedSize(true)
        binding.areasRecylerview.layoutManager = LinearLayoutManager(context)

        viewModel.areas.observe(viewLifecycleOwner) { areas ->
            if (!areas.isNullOrEmpty()) {
                context?.let {
                    val adapter = AreasRecyclerAdapter(it, areas)
                    binding.areasRecylerview.adapter = adapter
                }
            }
        }

        viewModel.getAreas()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
