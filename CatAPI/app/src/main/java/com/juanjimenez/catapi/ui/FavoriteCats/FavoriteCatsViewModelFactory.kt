package com.juanjimenez.catapi.ui.FavoriteCats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.juanjimenez.catapi.services.CatApiService

class FavoriteCatsViewModelFactory(private val catApiService: CatApiService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteCatsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoriteCatsViewModel(catApiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
