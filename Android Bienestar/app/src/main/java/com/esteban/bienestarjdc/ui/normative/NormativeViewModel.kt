package com.esteban.bienestarjdc.ui.normative

import androidx.lifecycle.ViewModel
import com.esteban.bienestarjdc.data.Normative
import com.esteban.bienestarjdc.repository.NormativeRepository
import io.reactivex.Observable

class NormativeViewModel(private val normativeRepository: NormativeRepository): ViewModel() {

    fun getNormatives(): Observable<List<Normative>> {
        return normativeRepository.getNormatives()
    }

}