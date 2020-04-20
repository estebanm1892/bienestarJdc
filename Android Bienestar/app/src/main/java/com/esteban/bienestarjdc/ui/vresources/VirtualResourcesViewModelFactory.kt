package com.esteban.bienestarjdc.ui.vresources

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.esteban.bienestarjdc.repository.VirtualResourceRepository

class VirtualResourcesViewModelFactory(private val virtualResourceRepository: VirtualResourceRepository): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return VirtualResourcesViewModel(
            virtualResourceRepository
        ) as T
    }

}