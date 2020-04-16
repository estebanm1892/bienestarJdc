package com.esteban.bienestarjdc.ui.publication

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.esteban.bienestarjdc.data.Publication
import com.esteban.bienestarjdc.repository.PublicationRepository
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

class PublicationViewModel(private val publicationRepository: PublicationRepository) : ViewModel() {

    fun getPublications(): Observable<List<Publication>> {
        return publicationRepository.getPublications()
    }

}