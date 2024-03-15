package com.juanjimenez.catapi.ui.RandomCat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanjimenez.catapi.models.CatFavoriteResponse
import com.juanjimenez.catapi.models.CatImage
import com.juanjimenez.catapi.models.FavoriteCatRequest
import com.juanjimenez.catapi.services.CatApiService
import kotlinx.coroutines.launch

class RandomCatViewModel(private val catApiService: CatApiService) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _catImage = MutableLiveData<CatImage?>()
    val catImage: LiveData<CatImage?> = _catImage

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean> = _success

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    private val _favorite = MutableLiveData<CatFavoriteResponse?>()
    val favorite: LiveData<CatFavoriteResponse?> = _favorite

    init {
        fetchRandomCatImage()
    }

    fun fetchRandomCatImage() {
        _isLoading.value = true
        _favorite.value = null

        viewModelScope.launch {
            try {
                val response = catApiService.getRandomCat()
                if (response.isSuccessful && response.body() != null) {
                    _catImage.postValue(response.body()!!.first())
                    _success.postValue(true)
                    _error.postValue(false)
                } else {
                    _errorMessage.postValue("Failed to load image")
                    _success.postValue(false)
                    _error.postValue(true)
                }
            } catch (e: Exception) {
                _errorMessage.postValue(e.message)
                _success.postValue(false)
                _error.postValue(true)
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun createFavorite(imageId: String, subId: String) {
        viewModelScope.launch {
            try {
                val requestBody = FavoriteCatRequest(imageId, subId)
                val response = catApiService.createFavoriteCat(requestBody)
                if (response.isSuccessful) {
                    _success.value = true
                    _error.value = false
                    _favorite.value = response.body()
                } else {
                    _errorMessage.value = response.errorBody()?.string()
                    _error.value = true
                    _success.value = false
                }
            } catch (e: Exception) {
                _errorMessage.value = e.localizedMessage
                _error.value = true
                _success.value = false
            }
        }
    }

    fun deleteFavorite(favoriteId: Int) {
        viewModelScope.launch {
            try {
                val response = catApiService.deleteFavoriteCat(favoriteId)
                if (response.isSuccessful) {
                    _success.postValue(true)
                    _error.postValue(false)
                    _favorite.postValue(null)
                } else {
                    _success.postValue(false)
                    _error.postValue(true)
                }
            } catch (e: Exception) {
                _success.postValue(false)
                _error.postValue(true)
            }
        }
    }

    fun resetErrorMessageState() {
        _success.postValue(false)
        _error.postValue(false)
        _errorMessage.value = null
    }
}
