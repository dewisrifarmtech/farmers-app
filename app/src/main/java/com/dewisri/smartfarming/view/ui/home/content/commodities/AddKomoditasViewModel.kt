package com.dewisri.smartfarming.view.ui.home.content.commodities.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dewisri.smartfarming.data.source.local.model.Kebun
import com.dewisri.smartfarming.data.source.repository.KomoditasRepository
import com.dewisri.smartfarming.data.source.repository.response.ResultOf
import javax.inject.Inject

class addKomoditasViewModel(private val komoditasRepository: KomoditasRepository): ViewModel() {


    fun getStatusKomoditas(): LiveData<ResultOf<String>> = komoditasRepository.saveResult

    fun getKomoditas(kebun: Kebun){
        komoditasRepository.saveKebunDetails(kebun)
    }

}