package com.esteban.bienestarjdc.ui.activity.Prepregister

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.esteban.bienestarjdc.repository.PreregisterRepository

class PreregisterViewModelFactory(private val preregisterRepository: PreregisterRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PreregisterViewModel(
            preregisterRepository
        ) as T
    }

}