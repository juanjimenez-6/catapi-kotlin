package com.juanjimenez.catapi.ui.RandomCat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.juanjimenez.catapi.services.CatApiService

class RandomCatViewModelFactory(private val catApiService: CatApiService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RandomCatViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RandomCatViewModel(catApiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
