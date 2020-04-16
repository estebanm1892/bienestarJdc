package com.esteban.bienestarjdc.ui.area

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.esteban.bienestarjdc.repository.AreaRepository

@Suppress("UNCHECHED_CAST")
class AreaViewModelFactory(private val areaRepository: AreaRepository): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AreaViewModel(
            areaRepository
        ) as T
    }

}