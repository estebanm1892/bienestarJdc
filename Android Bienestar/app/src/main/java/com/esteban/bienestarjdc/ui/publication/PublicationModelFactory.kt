package com.esteban.bienestarjdc.ui.publication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.esteban.bienestarjdc.repository.PublicationRepository

@Suppress("UNCHECHED_CAST")
class PublicationModelFactory(private val publicationRepository: PublicationRepository): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PublicationViewModel(
            publicationRepository
        ) as T
    }

}