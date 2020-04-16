package com.esteban.bienestarjdc.ui.area

import androidx.lifecycle.ViewModel
import com.esteban.bienestarjdc.data.Area
import com.esteban.bienestarjdc.repository.AreaRepository
import io.reactivex.Observable

class AreaViewModel (private val areaRepository: AreaRepository) : ViewModel() {

    fun getAreas(): Observable<List<Area>> {
        return areaRepository.getAreas()
    }

}