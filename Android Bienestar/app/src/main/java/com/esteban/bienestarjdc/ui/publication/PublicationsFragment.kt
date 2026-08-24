package com.esteban.bienestarjdc.ui.publication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.esteban.bienestarjdc.databinding.FragmentPublicationsBinding
import com.esteban.bienestarjdc.network.MyApi
import com.esteban.bienestarjdc.repository.PublicationRepository

/**
 * A simple [Fragment] subclass.
 */
class PublicationsFragment : Fragment() {

    private lateinit var viewModel: PublicationViewModel
    private var _binding: FragmentPublicationsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPublicationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val apiService = MyApi()
        val publicationRepository = PublicationRepository(apiService)
        val factory = PublicationViewModelFactory(publicationRepository)
        viewModel = ViewModelProvider(this, factory)[PublicationViewModel::class.java]

        binding.publicationsRecylerview.setHasFixedSize(true)
        binding.publicationsRecylerview.layoutManager = LinearLayoutManager(context)

        viewModel.publications.observe(viewLifecycleOwner) { publications ->
            if (!publications.isNullOrEmpty()) {
                context?.let {
                    val adapter = PublicationsRecyclerAdapter(it, publications)
                    binding.publicationsRecylerview.adapter = adapter
                }
            }
        }

        viewModel.getPublications()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
