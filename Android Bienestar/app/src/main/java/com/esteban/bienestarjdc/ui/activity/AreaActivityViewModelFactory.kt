package com.esteban.bienestarjdc.ui.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.esteban.bienestarjdc.repository.ActivityRepository

class AreaActivityViewModelFactory(private val activityRepository: ActivityRepository): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AreaActivityViewModel(
            activityRepository
        ) as T
    }

}