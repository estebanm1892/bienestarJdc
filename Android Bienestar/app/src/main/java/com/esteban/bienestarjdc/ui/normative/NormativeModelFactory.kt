package com.esteban.bienestarjdc.ui.normative

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.esteban.bienestarjdc.repository.NormativeRepository

class NormativeModelFactory(private val normativeRepository: NormativeRepository): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NormativeViewModel(
            normativeRepository
        ) as T
    }

}